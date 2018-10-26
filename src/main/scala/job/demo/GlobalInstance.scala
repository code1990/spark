package job.demo

;

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.hive.HiveContext

import scala.collection.mutable.ArrayBuffer

object GlobalInstance {

    var lineSeparator = "\n"
    var nullable = true
    var notNullable = true

    var hdfsUris = Array("hdfs://", "hdfs://")
    var hdfsWorkPath = "/root"
    var hdfsUserName = "root"

    def getHdfsFileSystem(hdfsUri: String, userName: String): FileSystem = {
        var fileSystem: FileSystem = null
        if (userName == null) {
            fileSystem = FileSystem.get(new URI(hdfsUri), getHadoopConfiguration())
        } else {
            fileSystem = FileSystem.get(new URI(hdfsUri), getHadoopConfiguration(), userName)
        }
        fileSystem
    }

    def getHadoopConfiguration(): Configuration = {
        val configuration = new Configuration()
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem")
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.fs.LocalFileSystem")
        configuration
    }

    /*检查当前返回的有效hdfs-uri*/
    def checkHdfsUri(): Array[String] = {
        var mainUri = new ArrayBuffer[String](5)
        hdfsUris.foreach(uri => {
            try {
                var fileSystem = GlobalInstance.getHdfsFileSystem(uri, hdfsUserName)
                val outStram = fileSystem.create(new Path(hdfsWorkPath + "test.txt"), true)
                outStram.writeBytes(uri)
                outStram.close()
                mainUri += uri

            } catch {
                case e: Exception => println("无法连接到 hdfs uri" + uri + "错误" + e.getMessage)
            }
        })
        mainUri.toArray
    }

    def setGlobalHadoopUser(user: String): Unit = {
        System.setProperty("HADOOP_USER_NAME", user)
    }

    object SQLContextSingleton {
        @transient private var instance: SQLContext = _

        def getInstance(sparkContext: SparkContext): SQLContext = {
            if (instance == null) {
                instance = new HiveContext(sparkContext)
            }
            instance
        }
    }

    object HiveContextSingleton {
        @transient private var instance: HiveContext = _

        def getInstance(sparkContext: SparkContext): HiveContext = {
            if (instance == null) {
                instance = new HiveContext(sparkContext)
            }
            instance
        }
    }

}
