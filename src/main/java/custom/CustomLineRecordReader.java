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
public class CustomLineRecordReader {

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










}
