package job.demo
import java.io._
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.regex.Pattern
//import java.util.zip.{ZipEntry, ZipInputStream}


import scala.collection.mutable.ArrayBuffer
import scala.io.Source
object FileReader {
    var path = "D:\\test.sql"
    def main(args: Array[String]): Unit = {
        val txt = Source.fromFile(new File(path)).getLines()
        var sql = new ArrayBuffer[String]()
        while (txt.hasNext){
            val line = txt.next()
            if (!line.isEmpty && line.startsWith("--")){
                sql += line
            }
            if(line.endsWith(";")){
                println(sql.mkString(" "))
                sql.clear()
            }
        }

    }


}
