package job.demo

import java.io._

import org.apache.commons.net.ftp
import org.apache.commons.net.ftp.{FTP, FTPClient}
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.IOUtils
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
/*ftp下载文件到hdfs*/
object FtpFilesToHdfs {

    var NULLABLE = true
    //生产环境hdfs主备uri
    val hdfsUris = Array("hdfs://", "hdfs://")
    var hdfsMasterBase = "hdfs://"
    var hdfsMasterPath = hdfsMasterBase + "/user/oas/"
    var ftpIP = ""
    var ftpUser = "root"
    var ftpPW = "123456"
    /*hdfs目录*/
    var hdfsWorkPath = "/home/"
    /*ftp目录*/
    var ftpBasePath = "/"
    /*hdfs用户名*/
    var hdfsUserName = "oas"
    /*ftp文件reg*/
    var regExp = "^[A-Za-z]{3}_(SEGMENT|CLASS){1}_\\d{8}\\.csv$"
    /*ftp目录搜索深度*/
    var dept = 7
    /*保存下载记录前10000条的文件路径*/
    var downloadedLogFilePath = "/user/oas/downloaded_ftp_files.txt"
    var transLower = false
    /*保存下载记录前10000条的set*/
    var downloadedFiles = new mutable.HashSet[(String, Long)]()

    /*保存下载记录前10000条的set*/
    def main(args: Array[String]): Unit = {
        if (args != null) {
            println("args.length: " + args.length)
            for (s <- args) {
                println("args: " + s)
            }
        }
        if (args != null && args.length > 4) {
            ftpBasePath = args(0)
            hdfsWorkPath = args(1)
            hdfsUserName = args(2)
            regExp = args(3)
            dept = Integer.valueOf(args(4))
            if(args.length > 5) transLower = args(5).toBoolean
        }

        val conf = new SparkConf().setAppName("ftp files download")
                .set("spark.sql.parquet.compression.codec","uncompressed")
        //gzip
        val spark = new SparkContext(conf)
        System.setProperty("HADOOP_USER_NAME",hdfsUserName)
        try{
            //先检查主备状态
            val validUri = GlobalInstance.checkHdfsUri()
            if (validUri.length >0){
                hdfsMasterBase = validUri(0)
                println("----start hdfs"+hdfsMasterBase)
                downloadedFiles = readDownloadLogFile
                ftpFilesDownload(hdfsMasterPath,ftpBasePath,regExp,dept)
            }else{
                println("---no valid hdfs")
            }
        }catch {
            case e:Exception => e.printStackTrace()
        }
        spark.stop()
    }

    private def readDownloadLogFile:mutable.HashSet[(String,Long)]={
        val set = new mutable.HashSet[(String,Long)]()
        try{

        }catch {
            case e:Exception=>e.printStackTrace()
        }
        set
    }

    /*ftp文件下载*/
    private def ftpFilesDownload(
                                hdfsPath:String,
                                ftpPath:String,
                                regExp:String,
                                dept:Int
                                )={
        val files = new ArrayBuffer[(String,Long)](5000)
        var ftp = ftpReconnect()
        ftpListFile(ftp,ftpPath,regExp,files,dept)
        ftp.changeWorkingDirectory(ftpPath)
        files.foreach{
            ftpFile =>
                try{
                    val file = ftpFile._1
                    val separatorIndex = file.lastIndexOf("/")
                    val fileName = file.substring(separatorIndex+1)
                    val filePath = file.substring(0,separatorIndex)
                    ftp = ftpReconnect()
                    ftp.changeWorkingDirectory(ftpPath)
                    val inSteam = ftp.retrieveFileStream(fileName)
                    val fileSystem =GlobalInstance.getHdfsFileSystem(hdfsMasterBase,hdfsUserName)
                    val outStream = fileSystem
                            .create(new Path(hdfsPath + (if(transLower) fileName.toLowerCase else fileName)), true) //文件名存为小写
                    IOUtils.copyBytes(inSteam,outStream,81920)
                    outStream.close()
                    inSteam.close()
                    ftp.disconnect()
                    println(file+"dowload")
                    downloadedFiles+=ftpFile
                }catch {
                    case e:Exception=>e.printStackTrace()
                }
        }

        //复写前10000个下载记录
        try{
            val fileSystem = GlobalInstance.getHdfsFileSystem(hdfsMasterBase,hdfsUserName)
            val outputStream = fileSystem.create(new Path(downloadedLogFilePath),true)
            val bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream))
            val result = downloadedFiles.toArray.sortWith((a,b)=>a._2>b._2)
            //记录最近下载的10000
            val size =if(result.length > 10000)10000 else result.length
            for ( i <- 0 until size){
                bufferedWriter.write(result.apply(i).toString())
                bufferedWriter.newLine()
            }
            bufferedWriter.close()
        }catch {
            case e:Exception=>e.printStackTrace()
        }
    }

    def ftpListFile(ftpClient:FTPClient,
                    ftpPath:String,
                    regExp:String,
                    files:ArrayBuffer[(String,Long)],
                    dept:Int): Unit ={
        var anotherDept = dept//复制基本类型int dept
        if(anotherDept <= 0){
            return
        }
        var ftp = ftpClient
        if(!ftp.isConnected){
            ftp = ftpReconnect()
        }
        val directory = ftpPath
        //更换目录到当前目录
        ftp.changeWorkingDirectory(directory)
        val ftpFiles = ftp.listFiles()
        anotherDept -= 1
        for (file <- ftpFiles){
            if(file.isFile){
                if(file.getName.matches(regExp)){
                    val one = (directory+file.getName,file.getTimestamp.getTimeInMillis)
                    if(!downloadedFiles.contains(one)){
                        files += one
                        println("download file:"+directory+file.getName+",size"+file.getSize)
                    }else{
                        println("ignore file"+directory+file.getName+"size"+file.getSize)
                    }
                }else{
                    ftpListFile(ftp,directory+file.getName+"/",regExp,files,anotherDept)
                }
            }
        }
    }

    def ftpReconnect():FTPClient = {
        val ftp = new FTPClient()
        ftp.setControlEncoding("UTF-8")
        ftp.setDefaultTimeout(72000)
        ftp.connect(ftpIP,21)
        ftp.enterLocalPassiveMode()
        ftp.login(ftpUser,ftpPW)
        ftp.setFileType(FTP.BINARY_FILE_TYPE)
        ftp
    }








}
