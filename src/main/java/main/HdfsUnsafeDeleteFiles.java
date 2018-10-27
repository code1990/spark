package main;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

@Deprecated
public class HdfsUnsafeDeleteFiles {

    public static void main(String[] args) {
        String deletePath = "/home";
        String hdfsMaster="hdfs://";
        String userName = "impala";
        if (args != null && args.length == 3) {
            hdfsMaster = args[0];
            userName = args[1];
            deletePath = args[2];
            for (String arg : args) {
                System.out.println(arg);
            }
        } else {
            System.out.println("input params");
            return;
        }
        //优先读取环境变量HADOOP_USER_NAME
        System.setProperty("HADOOP_USER_NAME",userName);
        Configuration configuration = new Configuration();
        configuration.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");
        configuration.set("fs.hdfs.impl","org.apache.hadoop.fs.LocalFileSystem");
        Path path = new Path(hdfsMaster);
        boolean result = false;
        try{
            result = path.getFileSystem(configuration).delete(new Path(hdfsMaster+deletePath),true);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (result){
            System.out.println("delete success");
        }else {
            System.out.println("delete fail");
        }
    }










}
