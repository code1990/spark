package custom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.Seekable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CompressedSplitLineReader;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.SplitLineReader;
import org.apache.hadoop.mapreduce.lib.input.UncompressedSplitLineReader;

import java.io.IOException;

/*根据LineRecorder修改key追加文件名*/
@InterfaceAudience.LimitedPrivate({"MapReduce", "Pig"})
@InterfaceStability.Evolving
public class CustomLineRecordReader extends RecordReader<String,Text>{

    public static final String MAX_LINE_LENGTH =
            "mapreduce.input.linerecordreader.line.maxlength";
    public static final String KEY_SPLIT = "@";
    private static final Log LOG = LogFactory.getLog(CustomLineRecordReader.class);
    private long start;
    private long pos;
    private long end;
    private SplitLineReader in;
    private FSDataInputStream fileIn;
    private Seekable filePosition;
    private int maxLineLength;
    private String fileName;
    private LongWritable filePartLength;
    private String key;
    private Text value;
    private boolean isCompressedInput;
    private Decompressor decompressor;
    private byte[] recordDelimiterBytes;

    public CustomLineRecordReader() {
    }

    public CustomLineRecordReader(byte[] recordDelimiterBytes) {
        this.recordDelimiterBytes = recordDelimiterBytes;
    }

    @Override
    public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        FileSplit split = (FileSplit)inputSplit;
        Configuration job = taskAttemptContext.getConfiguration();
        this.maxLineLength = job.getInt(MAX_LINE_LENGTH,Integer.MAX_VALUE);
        start = split.getStart();
        end = start + split.getLength();
        final Path file = split.getPath();

        fileName = file.getName();
        final FileSystem fs = file.getFileSystem(job);
        fileIn = fs.open(file);

        CompressionCodec codec = new CompressionCodecFactory(job).getCodec(file);
        if(null == codec){
            isCompressedInput = true;
            decompressor = CodecPool.getDecompressor(codec);
            if (codec instanceof SplittableCompressionCodec){
                final SplitCompressionInputStream cIn =
                        ((SplittableCompressionCodec) codec).createInputStream(
                                fileIn, decompressor, start, end,
                                SplittableCompressionCodec.READ_MODE.BYBLOCK);
                in= new CompressedSplitLineReader(cIn,job,this.recordDelimiterBytes);
                start = cIn.getAdjustedStart();
                end = cIn.getAdjustedEnd();
                filePosition = cIn;
            }else{
                fileIn.seek(start);
                in = new UncompressedSplitLineReader(fileIn,job,this.recordDelimiterBytes,split.getLength());
                filePosition = fileIn;
            }
            if (start != 0){
                start += in.readLine(new Text(),0,maxBytesToCosume(start));
            }
            this.pos =start;
        }
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if(filePartLength == null){
            filePartLength = new LongWritable();
        }
        filePartLength.set(pos);
        if (value == null){
            value = new Text();
        }
        key = fileName +KEY_SPLIT + filePartLength;
        int newSize = 0;
        try {
            while (getFilePosition() <= end || in.needAdditionalRecordAfterSplit()){
                if(pos ==0){
                    newSize = skipUtfByteOrderMark();
                }else{
                    newSize = in.readLine(value,maxLineLength,maxBytesToCosume(pos));
                    pos += newSize;
                }

                if((newSize==0)||(newSize < maxLineLength)){
                    break;
                }
                LOG.info("Skipped line of size"+newSize+"at pos"+(pos-newSize));
                if (newSize ==0){
                    filePartLength = null;
                    fileName = null;
                    value = null;
                    return false;
                }else{
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        float result = 0.0f;
        if(start == end){
            return 0.0f;
        }else{
            try {
                result = Math.min(1.0f,(getFilePosition()-start)/(float)(end-start));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public synchronized void close() throws IOException {
        try{
            if (in != null){
                in.close();
            }
        }finally {
            if (decompressor !=  null){
                CodecPool.returnDecompressor(decompressor);
                decompressor = null;
            }
        }
    }

    private long getFilePosition() throws Exception{
        long retVal;
        if(isCompressedInput && null != filePosition){
            retVal = filePosition.getPos();
        }else{
            retVal = pos;
        }
        return  retVal;
    }

    private int maxBytesToCosume(long pos){
        return isCompressedInput?Integer.MAX_VALUE:(int)Math.max(Math.min(Integer.MAX_VALUE,end-pos),maxLineLength);
    }

    private int skipUtfByteOrderMark() throws Exception{
        int newMaxLineLength = (int)Math.min(3L+(long)maxLineLength,Integer.MAX_VALUE);
        int newSize = in.readLine(value,newMaxLineLength,maxBytesToCosume(pos));
        pos += newSize;
        int textLength = value.getLength();
        byte[] textBytes = value.getBytes();
        if ((textLength >= 3) && (textBytes[0] == (byte) 0xEF) &&
                (textBytes[1] == (byte) 0xBB) && (textBytes[2] == (byte) 0xBF)) {
            LOG.info("found UTF-8 BOM skipped it");
            textLength -= 3;
            newSize -=3;
            if(textLength >0){
                textBytes=value.copyBytes();
                value.set(textBytes,3,textLength);
            }else{
                value.clear();
            }

        }
        return newSize;
    }
}
