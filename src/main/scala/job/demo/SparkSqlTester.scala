package job.demo

import org.apache.spark.{SparkConf, SparkContext}



/*spark-sql,dataset,dataframe的处理例子*/
object SparkSqlTester {
    val NULLABLE = true
    var hdfsMasterPath = "/home"
    var txtFilePath = ""
    var parquetFilePath = "spark-parquets/gjet-parquet"
    var userName = "hive"
    var ftpRegExp = "^[A-Za-z]{3}_(SEGMENT|CLASS){1}_\\d{8}\\.csv$"
    var ftpBasePath = "/"

    def main(args: Array[String]): Unit = {
        if (args != null && args.length > 1) {
            txtFilePath = args(0)
            parquetFilePath = args(1)
        }
        val conf = new SparkConf().setAppName("Spark Parquet Writer")
                .set("spark.sql.parquet.compression.codec","uncomressed")
                .setMaster("local[*]")
        //gzip
        val spark = new SparkContext(conf)
        System.setProperty("HADOOP_USER_NAME","hive")
        fdlOrgSql(spark)
        println("test".matches(ftpRegExp))


    }

    //处理数据入库
    private def fdlOrgSql(spark:SparkContext)={
        val intrdd = spark.textFile("file://D:/test.txt")
                .map(s=>s.split("\t"))
                .map{arr =>FdlOrg(
                    arr(0), arr(1).toInt,
                    arr(2)
                )}

        val sqlContext = GlobalInstance.SQLContextSingleton.getInstance(spark)
        val dataFrame = sqlContext.createDataFrame(intrdd)
        dataFrame.registerTempTable("tmp_test")
        sqlContext.sql("select * from test").toDF().registerTempTable("tmp_test_1")
        sqlContext.sql("select * from tableA").show()
        sqlContext.sql("select * from tableB").show()
        sqlContext.sql("")


    }

    case class FdlOrg(
                     CREATE_TIME:String,
                     DATA_OFFSET:Int,
                     DAY_FLAG:String
                     )












}
