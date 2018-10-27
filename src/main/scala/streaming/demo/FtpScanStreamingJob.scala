package streaming.demo

import java.io.File

//import job.demo.GlobalInstance
import org.apache.commons.net.ftp.{FTP, FTPClient}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Duration, StreamingContext}
import org.apache.spark.{Logging, SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source


object FtpScanStreamingJob {
    var timePattern = "yyyy-MM-dd HH:mm:ss.SSS"
    var hdfsMasterBase = "hdfs:ip:ipport"
    //主备hdfs
    var regExp = ""
    var hdfsWorkPath = ""
    var ftpBasePath = ""
    var hdfsUserName = ""
    var depth = 1
    var ftpIp = ""
    var ftpUser = ""
    var ftpPwd = ""
    var downloadedFiles = new ArrayBuffer[(String, Long)]()
    var downloadedFilePath = "test.txt"
    var recordNumber = 10000

    /*spark-steaming监控处理 清洗入库*/
    def main(args: Array[String]): Unit = {
        if (args != null) {
            println("args.length" + args.length)
            for (s <- args) {
                println("args" + s)
            }
            if (args.length > 5) {
                ftpBasePath = args(0) //扫描fto文件夹路径
                hdfsWorkPath = args(1) //hdfs工作目录
                hdfsUserName = args(2) //hdfs连接用户名
                regExp = args(3) //文件名正则表达式
                depth = Integer.valueOf(args(4)) //扫描深度
                recordNumber = Integer.valueOf(args(5)) //记录文件最大个数
            }

            val conf = new SparkConf().setAppName("Cabin Analyse Streaming Data Import")
                    .set("spark.sql.parquet.comression.codec", "uncompressed")
                    .set("spark.streaming.receiverRestartDelay", "5000") //重启receiver 每隔15秒重启一次
                    .setMaster("local[*]")

            //gzip
            val sparkContext = new SparkContext(conf)
            System.setProperty("HADOOP_USER_NAME", "")
            //关闭无关日志
            Logger.getLogger("org.apache.spark").setLevel(Level.OFF)
            Logger.getLogger("log4j.rootCategory").setLevel(Level.WARN)
            Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
            Logger.getLogger("org.apache.kafka.clients.consumer").setLevel(Level.OFF)

            //检查主备 hdfs uri
            /*val urls = GlobalInstance.checkHdfsUri()
            if(urls.length >0) hdfsMasterBase = urls(0)*/
            //获取历史已经下载文件列表
            val fileLogs = readDownloadLogFile()
            //广播到各个executor中
            sparkContext.broadcast(fileLogs)
            //1.实现AccumulabelParam[R,T] Traits 使得可以使用HashMap作为accumulator
            val accumulableCollection = sparkContext.accumulableCollection[
                    mutable.HashSet[
                            (String, Long)
                            ],
                    (String, Long)
                    ](new mutable.HashSet[(String, Long)]())

            fileLogs.foreach {
                file => accumulableCollection.add(file)
            }

            /*Duration 累计成DStream的间隔 跟Spark.streaming.receiverRestartDelay配合 如果小于 receRDelay会出现返回空的rdd集合的情况*/
            val ssc = new StreamingContext(sparkContext, Duration(10000L))
            ssc.receiverStream(new FTPReceiver())
                    .repartition(1)
                    .filter { file =>
                        !fileLogs.contains(file)
                    }
                    .foreachRDD {
                        rdd =>
                            rdd.distinct().foreach {
                                file =>
                                    println(file)
                                    fileLogs.add(file)
                            }
                    }
            ssc.start()
            ssc.awaitTermination()
        }


    }

    /*读取ftp下载记录文件*/
    private def readDownloadLogFile(): mutable.HashSet[(String, Long)] = {
        val set = new mutable.HashSet[(String, Long)]()
        try {
            //val fileSystem = GlobalInstance.getHdfsFileSystem(hdfsMasterBase, hdfsUserName)
            //val lines = Source.fromInputStream(fileSystem.open(new Path(downloadedLogFilePath))).getLines
            val lines = Source.fromFile(new File("D:")).getLines()
            while (lines.hasNext) {
                val s = lines.next()
                val arr = s.substring(1, s.length - 1).split(",")
                set.add((arr(0), arr(1).toLong))
            }
        } catch {
            case e: Exception => e.printStackTrace()
        }
        set
    }

    /*自定义ftp receiver 返回扫描目录下所有符合要求的文件*/
    class FTPReceiver extends Receiver[(String, Long)](StorageLevel.MEMORY_ONLY) with Logging {
        def onStart(): Unit = {
            new Thread("Ftp Server") {
                override def run(): Unit = {
                    receive()
                }
            }.start()
        }

        override def onStop(): Unit = {

        }

        private def receive(): Unit = {
            try {
                val ftpClient = ftpReconnect()
                val files = new ArrayBuffer[(String, Long)](5000)
                ftpListFile(ftpClient, ftpBasePath, regExp, files, depth)
                store(files)
                restart("trying to connect again")
            } catch {
                case e: Exception =>
                    restart(s"Error connecting to Ftp", e)
                case t: Throwable =>
                    restart("Error receiving data", t)
            }
        }

        /*递归遍历ftp文件*/
        private def ftpListFile(ftpClient: FTPClient,
                                ftpPath: String,
                                regExp: String,
                                files: ArrayBuffer[(String, Long)],
                                dept: Int): Unit = {
            var anotherDept = dept; //复制基本类型Int
            if (anotherDept <= 0) {
                return
            }
            var ftp = ftpClient;
            if (!ftpClient.isConnected) {
                ftp = ftpReconnect()
            }
            var directory = ftpPath
            //更换目录到当前目录
            ftp.changeWorkingDirectory(directory)
            val ftpFiles = ftp.listFiles()
            anotherDept -= 1
            for (file <- ftpFiles) {
                if (file.isFile) {
                    if (file.getName.matches(regExp)) {
                        val one = (directory + file.getName, file.getTimestamp.getTimeInMillis)
                        files += one
                    }
                } else {
                    ftpListFile(ftp, directory + file.getName + "/", regExp, files, anotherDept)
                }
            }
        }

        private def ftpReconnect(): FTPClient = {
            val ftp = new FTPClient()
            ftp.setControlEncoding("UTF-8")
            ftp.setDefaultTimeout(72000)
            ftp.connect(ftpIp, 21)
            ftp.enterLocalPassiveMode()
            ftp.login(ftpUser, ftpPwd)
            ftp.setFileType(FTP.BINARY_FILE_TYPE)
            ftp
        }
    }


}
