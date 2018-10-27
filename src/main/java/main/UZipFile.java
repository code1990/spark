package main;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class UZipFile {

    /*解压文件到指定目录*/

    public static void unZipFiles(String zipPath, String descDir) throws Exception {
        File file = new File(zipPath);
        unZipFiles(file, descDir);
    }

    /*解压文件到指定目录*/
    public static void unZipFiles(File zipFile, String descDir) throws Exception {
        File file = new File(descDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        //解决zip文件中有中文目录或者中文文件
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
        for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
            //判断路径是否存在
            File file1 = new File(outPath.substring(0, outPath.lastIndexOf("/")));
            if (!file.exists()) {
                file.mkdirs();
            }
            //判断文件全部路径是否为文件夹 如果是上面已经上传 不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            //输出文件路径信息
            System.out.println(outPath);
            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        System.out.println("解压完");

    }

    public static void main(String[] args) throws Exception{
        File file = new File("D:/test.zip");
        String path = "D:/temp";
        unZipFiles(file,path);
        System.out.println(String.class);
        ArrayList zipFile = new ArrayList();
        System.out.println(zipFile.getClass().getComponentType());
    }
}
