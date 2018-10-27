package main;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CopyFiles {

    public static void main(String[] args) throws Exception {
        String outPath = "D:/temp/";
        String inPath = "D:/csv";
        List<String> files = new ArrayList<>();
        files.add("test.csv");
        files.add("test1.csv");
        for (String file:files) {
            byte[] buffer = new byte[8192];
            FileInputStream fis = new FileInputStream(new File(inPath+file));
            FileOutputStream fos = new FileOutputStream(new File(outPath +file));
            int length =0;
            while ((length=fis.read(buffer))>0){
                fos.write(buffer,0,length);
            }
            fis.close();
            fos.close();
        }
    }










}
