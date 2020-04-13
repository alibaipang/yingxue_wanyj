package com.baizhi.wyj.util;

import java.io.*;
import java.net.URL;

public class DownLoadUtil {

    public static void download(String path){
        try {
            //new一个URL对象
            URL url = new URL(path);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
            //保存目录
            String[] split = path.split("/");
            String fileName = split[split.length-1];
            //设置保存路径
            String savePath = "D:/Download";
            File dir = new File(savePath);
            if (!dir.exists()){
                dir.mkdir();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath+"/"+ fileName));
            //得到图片并保存
            byte[] bytes = new byte[100];
            int len;
            while ((len = bufferedInputStream.read(bytes)) != -1){
                fileOutputStream.write(bytes,0,len);
            }
            bufferedInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
