package custom;

import com.google.common.base.Charsets;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.SplittableCompressionCodec;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/*根据textInputformat修改 keys追加文件名*/
@InterfaceAudience.Public
@InterfaceStability.Stable
public class CustomTextInputFormat extends FileInputFormat<String,Text>{

    @Override
    public RecordReader<String, Text> createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {

        String delimiter = taskAttemptContext.getConfiguration().get("textinputformat.record.delimiter");
        byte[] recordDelimiterBytes = null;
        if(null != delimiter){
            recordDelimiterBytes = delimiter.getBytes(Charsets.UTF_8);
        }
        return new CustomLineRecordReader(recordDelimiterBytes);
    }

    @Override
    protected boolean isSplitable(JobContext context,Path file){
        final CompressionCodec codec =
                new CompressionCodecFactory(context.getConfiguration()).getCodec(file);
        if (null == codec){
            return true;
        }
        return codec instanceof  SplittableCompressionCodec;
    }
}
