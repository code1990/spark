package job.demo;

import java.sql.{Connection, DriverManager, ResultSet, Statement}
import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime}
import java.util.TimeZone


object HiveSqlTester {
    def main(args: Array[String]): Unit = {
        var map = getRegionMap()
        for (k <- map.keySet){
            println("key:"+k+",value="+map.get(k))
        }

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
            Class.forName("org.apache.hive.jdbc.HiveDriver")
            con = DriverManager.getConnection("jdbc:hive2://vm-mc2:2181,vm-mc3:2181,vm-mc4:2181/;" +
                    "serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2")
            stmt = con.createStatement()
            rs = stmt.executeQuery("show databases")

            while(rs.next()){
                regBuilder+=(rs.getString(1)->rs.getString(1))
            }
            println("---")
        }catch{
            case e:Exception=>e.printStackTrace()
        }finally {
            try {
                con.close()
            } catch {
                case e:Exception =>e.printStackTrace()
            }
        }
        regBuilder.result()
    }











}
