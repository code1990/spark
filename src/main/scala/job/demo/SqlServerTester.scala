package job.demo;

import java.sql.{Connection, DriverManager, ResultSet, Statement}
import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime}
import java.util.TimeZone

import scala.collection.mutable.ArrayBuffer


object SqlServerTester {

    var sqlserverJdbcUri = "jdbc:sqlserver://"
    var sqlserverUser = ""
    var sqlServerPassword =""

    def main(args: Array[String]): Unit = {
        getProcedure()
    }

    def getTransitCode():Map[String,String]={
        val regBuilder = Map.newBuilder[String,String]

        val timeString = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(System.currentTimeMillis),
            TimeZone.getDefault.toZoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))

        var con:Connection = null
        var stmt:Statement =null
        var rs:ResultSet = null
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
            con = DriverManager.getConnection(sqlserverJdbcUri+";user="+sqlserverUser+";password="+sqlServerPassword)
            stmt = con.createStatement()
            rs = stmt.executeQuery("select * from tableA")
            while (rs.next()){
                regBuilder +=(rs.getString(1)->rs.getString(2))
            }
        }catch {
            case e:Exception => e.printStackTrace()
        }finally {
            try {
                con.close()
            } catch {
                case e:Exception =>e.printStackTrace()
            }
        }
        regBuilder.result()
    }

    def getProcedure():ArrayBuffer[String]={

        var array = ArrayBuffer[String]()
        var con:Connection = null
        var stmt:Statement = null
        var rs:ResultSet=null
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
            con = DriverManager.getConnection(sqlserverJdbcUri+";user="+sqlserverUser+";password="+sqlServerPassword)
            stmt = con.createStatement()
            rs = stmt.executeQuery("select test from tableB")
            while (rs.next()){
                println(rs.getString("test"))
            }
        }catch {
            case e:Exception=>e.printStackTrace()
        }finally {
            try {
                con.close()
            } catch {
                case e:Exception =>e.printStackTrace()
            }
        }
        array.result()
    }

    def getRegionMap():Map[String,String]={
        val regBuilder = Map.newBuilder[String,String]
        val timeString = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(System.currentTimeMillis),
            TimeZone.getDefault.toZoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
        var con: Connection = null
        var stmt: Statement = null
        var rs: ResultSet = null
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.sqlServerDriver")
            con = DriverManager.getConnection(sqlserverJdbcUri+";user="+sqlserverUser+";password="+sqlServerPassword)
            stmt = con.createStatement()
            rs = stmt.executeQuery("select * from tableC")
            while (rs.next()){
                regBuilder+=(rs.getString(1)->rs.getString(2))
            }
        }catch {
            case e:Exception=>e.printStackTrace()
        }finally{
            try {
                con.close()
            } catch {
                case e:Exception =>e.printStackTrace()
            }
        }
        regBuilder.result()
    }







}
