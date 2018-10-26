package job.demo;

import java.sql.{Connection, DriverManager, ResultSet, Statement}
import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime}
import java.util.{TimeZone, UUID}

import com.csair.oas.custom.{CustomLineRecordReader, CustomTextInputFormat}
import com.csair.oas.job.demo.GlobalInstance
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.Text
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.execution.aggregate.Utils
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.streaming.{Duration, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer


object CabinAnalysisImportJob {











}
