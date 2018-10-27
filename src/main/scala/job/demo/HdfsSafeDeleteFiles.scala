package job.demo

import org.apache.hadoop.fs.Path

/*指定hdfs用户 删除指定文件夹*/
object HdfsSafeDeleteFiles {
    var deletePath ="/home"
    var hdfsMaster = "hdfs://"
    var userName = "impala"

    def main(args: Array[String]): Unit = {
        if (args != null && args.length == 2) {
            userName = args(0)
            deletePath = args(1)
            println(args)
        } else {
            println("input params")
            return
        }

        //先检查hdfs的主备状态
        val validUri = GlobalInstance.checkHdfsUri()
        if(validUri.length >0){
            hdfsMaster = validUri(0)
            println("----start hdfs"+hdfsMaster)
            val fileSystem  = GlobalInstance.getHdfsFileSystem(hdfsMaster,userName)
            try{
                val files = fileSystem.listFiles(new Path(deletePath),true)
                while (files.hasNext){
                    val path = files.next().getPath
                    println("path name:"+path.getName)
                    if(fileSystem.delete(path,true)){
                        println("delete success:"+path)
                    }else{
                        println("delete "+path)
                    }
                }
                val result = fileSystem.delete(new Path(deletePath),true)
                if (result){
                    println("success"+deletePath)
                }else{
                    println("delete"+deletePath)
                }
            }
        }else {
            println("no --valid hdfs")
        }
    }
}
