package ru.stopkran.stopkaran.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ImageEncryptUtil {
    public static String loadImage(String path){
        File file = new File(path);
        if(file.exists()){
            try {
                return getImgData(Files.readAllBytes(file.toPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("file is empty");
            return null;
        }
    }
    public static String getImgData(byte[] byteData){
        return Base64.getMimeEncoder().encodeToString(byteData);
    }
}
