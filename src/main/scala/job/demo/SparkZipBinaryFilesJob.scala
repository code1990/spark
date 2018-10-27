package job.demo

;

import java.io.{IOException, InputStreamReader, LineNumberReader}
import java.util.UUID
import java.util.zip.{ZipEntry, ZipInputStream}

import org.apache.hadoop.fs.Path
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer


object SparkZipBinaryFilesJob {

    var hdfsMasterBase = "hdfs://"

    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("zip data Import")
                .set("spark.sql.parquet.compression.codec", "uncompressed")
        var spark = new SparkContext(conf)

        spark.binaryFiles("/home/*.zip")
                .foreach(file => {
                    val fileName = file._1
                    var lines = new ArrayBuffer[String](2048)
                    try {
                        val zipInputStream = new ZipInputStream(file._2.open())
                        try {
                            var entry: ZipEntry = zipInputStream.getNextEntry
                            while (entry != null) {
                                println("Entry: " + entry.getName + " Size: " + entry.getSize)
                                val fileSystem = GlobalInstance.getHdfsFileSystem(hdfsMasterBase, "oas")
                                val outStream = fileSystem
                                        .create(new Path("/home/" + UUID.randomUUID()), true)
                                try {
                                    val lineNumberReader = new LineNumberReader(new InputStreamReader(zipInputStream, "GBK"))
                                    var line = lineNumberReader.readLine()
                                    while (line != null) {
                                        outStream.write(line.getBytes)
                                        line = lineNumberReader.readLine()
                                    }
                                } finally {
                                    if (outStream != null) {
                                        outStream.close()
                                    }
                                }
                                entry = zipInputStream.getNextEntry
                            }
                        }
                        finally {
                            zipInputStream.close()
                        }
                    }
                    catch {
                        case var3: IOException =>
                            println("IOException thrown: ", var3)
                    }
                })
    }


}
