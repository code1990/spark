package main;

import job.demo.GlobalInstance;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*@Deprecated不推荐使用 修改为scala版本*/
@Deprecated
public class FtpFilesToHdfs {

    private static String hdfsMasterBase = "hdfs://";
    //"hdfs://"//
    private static String hdfsMasterPath = "hdfs://";
    private static String ftpIP = "";
    private static String ftpUser = "root";
    private static String ftpPW = "root123";
    /*hdfs目录*/
    private static String hdfsWorkPath = "/root";
    /*ftp目录*/
    private static String ftpBasePath = "/home/doc";
    /*hdfs用户名*/
    private static String hdfsUserName = "root";
    /*ftp文件reg*/
    private static String regExp = "^[A-Za-z]{3}_(SEGMENT|CLASS){1}_\\d{8}\\.csv$";
    /*ftp目录搜索深度*/
    private static int dept = 3;
    private boolean NULLABLE = true;

    public static void main(String[] args) {
        if(args != null){
            System.out.println("args length"+args.length);
            for (String s:args){
                System.out.println("args:"+s);
            }
        }
        if(args != null && args.length >4){
            ftpBasePath = args[0];
            hdfsWorkPath = args[1];
            hdfsUserName = args[2];
            regExp = args[3];
            dept = Integer.valueOf(args[4]);
        }
        SparkConf conf = new SparkConf().setAppName("ftp files download")
                .set("spark.sql.parquet.compression.codec","uncomoressed");
        //gzip
        SparkContext spark = new SparkContext(conf);
        System.setProperty("HADOOP_USER_NAME",hdfsUserName);
        try{
            System.out.println("==start==");
            ftpFilesDownload(hdfsWorkPath,ftpBasePath,regExp,dept);
            System.out.println("end");
        }catch (Exception e){
            e.printStackTrace();
        }
        spark.stop();
    }


    private static void ftpFilesDownload(
            String hdfsPath,
            String ftpPath,
            String regExp,
            int dept
    )throws Exception{
        List<String> files = new ArrayList<>(500);
        final FTPClient[] ftp = {ftpReconnect()};
        ftpListFile(ftp[0], ftpPath, regExp, files, dept);
        ftp[0].disconnect();
        for (String file:files){
            try{
                int separatorIndex = file.lastIndexOf("/");
                String fileName = file.substring(separatorIndex+1);
                String filePath = file.substring(0,separatorIndex);
                ftp[0] = ftpReconnect();
                ftp[0].changeWorkingDirectory(filePath);
                InputStream inputStream = ftp[0].retrieveFileStream(fileName);
                FileSystem fileSystem = GlobalInstanceJava.getHdfsFileSystem(hdfsMasterBase,hdfsUserName);
                FSDataOutputStream outputStream = fileSystem.create(new Path(hdfsPath+fileName),true);
                IOUtils.copyBytes(inputStream,outputStream,81920);
                System.out.println(file+"downloaded");
                outputStream.close();
                inputStream.close();
                ftp[0].disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    //递归遍历fatp文件
    private static void ftpListFile(FTPClient ftpClient,
                                    String ftpPath,
                                    String regExp,
                                    List<String> files,
                                    int dept) throws Exception{
        int anotherDept = dept;
        if (anotherDept <= 0){
            return;
        }
        FTPClient ftp = ftpClient;
        if(!ftp.isConnected()){
            ftp = ftpReconnect();
        }
        String directory = ftpPath;
        //更换目录到当前目录
        ftp.changeWorkingDirectory(directory);
        FTPFile[] ftpFiles = ftp.listFiles();
        anotherDept--;
        for (FTPFile file:ftpFiles) {
            if (file.isFile()){
                if(file.getName().matches(regExp)){
                    files.add(directory+file.getName());
                    System.out.println("file:"+directory+file.getName()+",size"+file.getSize());
                }
            }else {
                System.out.println("dir:"+directory+file.getName()+",dept:"+dept);
                ftpListFile(ftp,directory+file.getName()+"/",regExp,files,anotherDept);
            }

        }

    }

    private static FTPClient ftpReconnect() throws Exception{
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        ftp.setDefaultTimeout(72000);
        ftp.connect(ftpIP,21);
        ftp.login(ftpUser,ftpPW);
        ftp.enterLocalPassiveMode();
        return ftp;
    }


    private static FTPClient ftpReconnect(String ip,int port,String user,String password) throws Exception{
        FTPClient ftp = new FTPClient();
        ftp.connect(ip,port);
        ftp.login(user,password);
        return ftp;
    }


}
