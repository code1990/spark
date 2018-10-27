package streaming.demo

import java.sql.{Connection, DriverManager, ResultSet, Statement}
import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime}
import java.util.{TimeZone, UUID}

import custom.{CustomLineRecordReader, CustomTextInputFormat}
import job.demo.GlobalInstance
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.Text
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.execution.aggregate.Utils
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.streaming.{Duration, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

/*spark streaming样例 记录新到文件的行数*/
object CabinAnalysisStreamingJob {

    var NOFILES = "NONE"
    var timePattern = "yyyy-MM-dd HH:mm:ss.SSS"
    var hdfsMasterBase = "hdfs://"
    //监控csv文件目录
    var txtFilePath = "/root"
    //文件名称通配符
    var fileReg = "*CLASS*.csv"
    //文件保存路径
    var saveFilePath = "/home"
    var hiveJdbcUri = "jdbc:hive2://"
    var hiveUser = ""
    var hivePassword = ""

    /*sparkstreaming处理class文件清洗入库*/
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("cabin Analyse Streaming Data Import")
                .set("spark.sql.parquet.comression.codec", "uncompressed")

        //gzip
        val sparkContext = new SparkContext(conf)
        System.setProperty("HADOOP_USER_NAME", "oas")
        //关闭无关日志
        Logger.getLogger("org.apache.spark").setLevel(Level.OFF)
        Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
        Logger.getLogger("org.apache.kafka.clients.consumer").setLevel(Level.OFF)

        val ssc = new StreamingContext(sparkContext, Duration(10000L))
        val textDataStream = ssc.fileStream[String, Text, CustomTextInputFormat](
            txtFilePath,
            (path: Path) => {
                val result = path.getName.matches("^[A-Za-z]{3}_(CLASS){1}_\\d{8}\\.csv$") //过滤csv结尾的文件
                result
            },
            true //是否只读取新文件
        )

        //处理GBK编码问题
        val textDS = textDataStream.map(
            s=>{
                val bytes = s._2.getBytes
                val fileName = s._1.split(CustomLineRecordReader.KEY_SPLIT)(0)
                val columns = new String(bytes,0,bytes.length,"GBK").split(",")
                (fileName,columns)
            }
        )

        //删除临时目录
        GlobalInstance.getHdfsFileSystem(hdfsMasterBase,hiveUser).delete(new Path(saveFilePath),true)

        //执行入库
        textDS.foreachRDD(
            (rdd,time)=>{
                if(!rdd.isEmpty()){
                    //如果文件数据到达
                    val timeString = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(time.milliseconds),
                        TimeZone.getDefault.toZoneId
                    ).format(DateTimeFormatter.ofPattern(timePattern))

                    val finalData = rdd.map(item =>fileLineClean(item._2,item._1.substring(0,3),timeString)).collect()

                    //集中到dircer 保存为一个text文件
                    try{
                        val fileSystem = GlobalInstance.getHdfsFileSystem(hdfsMasterBase,hiveUser)
                        val outStream = fileSystem.create(new Path(saveFilePath+UUID.randomUUID()),true)
                        finalData.foreach(
                            line => outStream.write((line + GlobalInstance.lineSeparator).getBytes)
                        )
                        outStream.close()
                    }catch {
                        case e:Exception=>e.printStackTrace()
                    }
                }
            }
        )


        //执行最终表入库 执行refresh
        insertAndRefresh()
        ssc.start()
        ssc.awaitTermination()
    }

    def insertAndRefresh():Unit={
        //执行impala sql
        var con:Connection = null
        var stmt:Statement=null
        var rs:ResultSet =null
        val insetSql = "insert into tableA values()select * from tableB"
        try{
            Class.forName("org.apache.hive.jdbc.HiveDriver")
            con = DriverManager.getConnection(hiveJdbcUri,hiveUser,hivePassword)
            stmt = con.createStatement()
            stmt.execute(insetSql)
            stmt.execute("refresh tableA")
        }catch {
            case e:Exception=>e.printStackTrace()
        }finally {
            if(con != null){
                try{
                    con.close()
                }catch {
                    case e:Exception=>e.printStackTrace()
                }
            }
        }
    }

    /*每行数据清洗不对所有的入参做非空校验*/
    private def fileLineClean(array:Array[String],
                              company:String,
                              createTime:String):String={
        var result = new ArrayBuffer[String](100)
        array(0)= switchWeek(array(0))
        array(10)= castFltYearAndMonth(array(10))
        array(11)= array(11).replace("-","")
        array(14)= castFltYearAndMonth(array(14))
        array(16)= castFltWeek(array(0))

        result +="0"
        for(i <- 0 until 15){
            result ++ array(i)
        }
        for (i <- 16 until array.length){
            result += array(i)
        }
        result += company
        result += createTime
        result.mkString(",")
    }
    private def switchWeek(value:String):String={
        value match{
            case "周一"=> "1"
            case "周二"=> "2"
            case "周三"=> "3"
            case "周四"=> "4"
            case "周五"=> "5"
            case "周六"=> "6"
            case "周七"=> "7"
            case _ => "0"
        }
    }
    /*匹配提取正整数*/
    private def castFltYearAndMonth(read:String):String={
        read.substring(0,read.length-1)
    }

    private def castFltWeek(fltWeek:String):String={
        fltWeek.substring(1,fltWeek.length-1)
    }

    def buildCabinAnalyseSchema():StructType={
        StructType(Array(
            StructField("id",StringType,GlobalInstance.nullable),
            StructField("company",StringType,GlobalInstance.nullable),
            StructField("create_time",StringType,GlobalInstance.nullable),
        ))
    }








}
