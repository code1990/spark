package job.demo;

import java.sql.{Connection, DriverManager, ResultSet, Statement}

object ImpalaSqlJob {
    //放入yarn运行：需要jar包：
    /*
     libthrift-0.9.3.jar
     hive-service-1.1.0-cdh5.12.0.jar
     hive-jdbc-1.1.0-cdh5.12.0.jar
     hive-common-1.1.0-cdh5.12.0.jar
    */
    var jdbcUri = "jdbc:hive2://"
    var user = "hive"
    var password = "hive"

    def main(args: Array[String]): Unit = {
        if (args.length >= 3) {
            jdbcUri = args(0)
            user = args(1)
            password = args(2)
        }
        println("args: ")
        args.foreach(println(_))

        //执行Imapla.sql
        var con:Connection = null
        var stmt:Statement = null
        var rs:ResultSet = null

        try{
            Class.forName("org.apache.hive.jdbc.HiveDriver")
            con = DriverManager.getConnection(jdbcUri,user,password)
            stmt = con.createStatement()
            val rs:ResultSet= stmt.executeQuery("select * from tableA")
            while (rs.next()){
                println("result_set")
                println(rs.getString(1))
            }
        }catch {
            case e:Exception=>e.printStackTrace()
        }finally {
            if(con != null){
                try {
                    con.close()
                } catch {
                    case e:Exception =>e.printStackTrace()
                }
            }
        }
    }












}
