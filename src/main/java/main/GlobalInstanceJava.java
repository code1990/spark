package main;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class GlobalInstanceJava {

    public static Configuration getHadoopConfiguration(){
        Configuration configuration = new Configuration();
        configuration.set("fs.hdfs.impl","org,apache.hadoop.hdfs.DistributedFileSystem");
        configuration.set("fs.hdfs.impl","org,apache.hadoop.fs.LocalFileSystem");
        return configuration;
    }

    public static FileSystem getHdfsFileSystem(String hdfsUrl,String userName) throws Exception{
        FileSystem fileSystem;
        if (userName == null){
            fileSystem = FileSystem.get(new URI(hdfsUrl),getHadoopConfiguration());
        }else{
            fileSystem = FileSystem.get(new URI(hdfsUrl),getHadoopConfiguration(),userName);
        }
        return fileSystem;
    }

    public static  void setGlobalHadoopUser(String user){
        System.setProperty("HADOOP_USER_NAME",user);
    }












}
