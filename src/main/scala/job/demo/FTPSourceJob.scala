package job.demo

import org.apache.spark.{SparkConf, SparkContext, SparkFiles}

object FTPSourceJob {

    val NOFILES = "NONE"
    val hdfsMasterPath = "hdfs:"
    //从ftp保存的csv文件目录
    var txtFilePath = "/data"
    //从csv文件清洗后的目录
    var csvFinalFilePath = "/home"
    //字典表文件目录位置
    var dictionaryFilePath = "/opt/hive"
    //用户名称
    var userName = "root"
    //文件名称通配符
    var fileReg = "*CLASS*.csv"

    def main(args: Array[String]): Unit = {
        if (args != null && args.length > 3) {
            txtFilePath = args(0)
            csvFinalFilePath = args(1)
            dictionaryFilePath = args(2)
            fileReg = args(3)
        }
        val conf = new SparkConf().setAppName("spark file download")
                .set("spark.sql.parquet.compression.codec","uncompressed")
                .setMaster("local[*]")
        //gzip
        val spark = new SparkContext(conf)
        System.setProperty("HADOOP_USER_NAME",userName)

        val dataSource = "ftp://"
        spark.addFile(dataSource,true)
        var fileName = SparkFiles.get("test.csv")
        var file = spark.binaryFiles(fileName)
        println("file.count"+file.count())
    }










}
