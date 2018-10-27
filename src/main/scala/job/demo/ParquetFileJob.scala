package job.demo

import java.io._

import job.demo.FrontLineFDLImportJob.TcPrice
import org.apache.avro.generic.GenericData
import org.apache.hadoop.io.IOUtils
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.{ColumnName, Row, SQLContext}
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/*spark转parquet文件，读写分区操作例子*/
object ParquetFileJob {

    val NULLABLE = true

    val hdfsMasterPath = "/parquet/"
    var txtFilePath = "from_ftp/"

    var parquetFilePath = "spark-parquets"
    var userName = "hive"
    var ftpRegExp = "^[A-Za-z]{3}_(SEGMENT|CLASS){1}_\\d{8}\\.csv$"
    var ftpBasePath = "/"

    def main(args: Array[String]): Unit = {
        if (args != null && args.length > 1) {
            txtFilePath = args(0)
            parquetFilePath = args(1)
        }
        val conf = new SparkConf().setAppName("Spark Parquet Writer")
                .set("spark.sql.parquet.compression.codec", "uncompressed")
                .setMaster("local[*]")
        //gzip
        val spark = new SparkContext(conf)
        System.setProperty("HADOOP_USER_NAME","hive")
        //拷贝所有的csv文件到hdfs
        textFileModify(spark)

        val rdd = GlobalInstance.HiveContextSingleton.getInstance(spark)
                .read.parquet(hdfsMasterPath+"class")
                .map[Row](row=>Row(row.mkString("\t")))
        GlobalInstance.HiveContextSingleton.getInstance(spark)
                .createDataFrame(rdd, StructType(Array(StructField("data", StringType, NULLABLE))))
                .write.text("/data")

        GlobalInstance.HiveContextSingleton.getInstance(spark)
                .read.parquet(hdfsMasterPath+"test").printSchema()
        val tcPriceRdd = spark.textFile("/data"+"/test.txt")
                .repartition(1)
                .filter(_.nonEmpty)
                .map(_.split("\t"))
                .filter(arr => arr.length ==7)//只处理分割后有7个字符的记录
                .map{arr=>
            TcPrice(arr(0).trim(),arr(1).trim,arr(2).substring(0,10))
        }

        val hiveContext = GlobalInstance.HiveContextSingleton.getInstance(spark)
        hiveContext.createDataFrame(tcPriceRdd).registerTempTable("test")
        "hdfs://".split("/").last

        val initRdd = spark.binaryFiles("/data/test.txt")
                .map{
                    file =>
                        val fileName = file._1
                        try{
                            val newFileOutStream = new FileOutputStream(new File("/data"+fileName))
                            try{
                                IOUtils.copyBytes(file._2.open(),newFileOutStream,102400)
                            }finally {
                                newFileOutStream.close()
                            }
                        }catch {
                            case e:Exception=>e.printStackTrace()
                        }
                        fileName
                }.collect()
        initRdd.foreach(println(_))
    }

    /*按照date分区写入一个parquet文件*/
    def parquetFileWrite(spark:SparkContext)={
        val initrdd = spark.textFile(hdfsMasterPath+txtFilePath)
                .map(s => s.split(","))
                .map(mapGjetRow)
        val dataFrame = new SQLContext(spark)
                .createDataFrame(initrdd,getGjetSchema())
                .repartition(new ColumnName(("date")))
        dataFrame.write.partitionBy("date")
                .parquet(hdfsMasterPath+parquetFilePath)


    }

    /*读取text文件并修改字段内容在分区存储parquet*/
    def textFileModify(spark:SparkContext)={
        val sqlContext = new SQLContext(spark)
        val rdd1 = spark.textFile("/data")
                .map(l => l.split(","))
                .map[Row](a=>Row(a(0),a(1),a(3)))
        val df = sqlContext.createDataFrame(rdd1,fdlClassSchema())

        df.select(df("id"),df("company"),df("create_time")).repartition(1)
                .write.partitionBy("create_time").save("/data/test.text")//默认格式parquet
    }

    //读取parquet文件并修改字段内容
    def parquetFileRead2(spark:SparkContext)={
        val sqlContext = new SQLContext(spark)
        sqlContext.read.parquet("/data").show(100)
    }
    //读取parquet文件
    def parquetFileRead(spark:SparkContext)={
        val mainPath = new File(hdfsMasterPath+parquetFilePath)
        val parquetPartitionDirs = mainPath.listFiles(new FileFilter {
            override def accept(pathname: File): Boolean = {
                pathname.isDirectory
            }
        })

        val sqlContext = new SQLContext(spark)
        val resultFile = new BufferedWriter(new FileWriter("D:\\sdk\\test.txt"))
        parquetPartitionDirs.foreach(
            dir => {
                dir.listFiles(new FileFilter {
                    override def accept(pathname: File): Boolean = {
                        pathname.getName.endsWith(".parquet")
                    }
                }).foreach(
                    file => {
                        val ticketDates = sqlContext.read.parquet("file:///" + file.getAbsolutePath)
                                .select("ticket_date")
                                .collect()
                                .distinct
                        resultFile.write("----------" + file.getAbsoluteFile)
                        resultFile.newLine()
                        ticketDates.foreach(
                            dateString => {
                                resultFile.write(dateString.toString())
                                resultFile.newLine()
                            }
                        )
                    }
                )
            }
        )
        resultFile.close()
    }
    def fdlClassSchema():StructType={
        StructType(Array(
            StructField("id",StringType,NULLABLE),
            StructField("company",StringType,NULLABLE),
            StructField("create_time",StringType,NULLABLE)
        ))
    }

    def getGjetSchema():StructType={
        StructType(Array(
            StructField("id",StringType,NULLABLE),
            StructField("company",StringType,NULLABLE),
            StructField("create_time",StringType,NULLABLE)
        ))
    }
    def mapGjetRow(arr:Array[String]):Row={
        Row(
            arr(0),arr(1),arr(3)
        )
    }



    def parquetFileCount(spark:SparkContext)={
        val mainPath = new File(hdfsMasterPath+parquetFilePath)
        val parquetFileFiles = mainPath.listFiles(new FileFilter {
            override def accept(pathname: File): Boolean = {
                pathname.getName.endsWith("parquet")
            }
        })
        val sqlContext = new SQLContext(spark)
        println("---"+sqlContext.read.parquet(hdfsMasterPath+parquetFileFiles).count())
    }

    class TicketDatePartioner(numParts:Int)extends Partitioner{
        override def numPartitions: Int = numParts

        override def getPartition(key: Any): Int = {
            key.toString.replaceAll(key.toString,"-").toInt
        }

        override def equals(obj: Any): Boolean = super.equals(obj)

        override def hashCode(): Int = super.hashCode()
    }

}
