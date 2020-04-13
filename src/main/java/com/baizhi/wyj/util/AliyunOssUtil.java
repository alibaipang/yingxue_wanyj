package com.baizhi.wyj.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class AliyunOssUtil {

    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    /**
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，
     * 请登录 https://ram.console.aliyun.com 创建RAM账号。
     * */

    private static String accessKeyId = "LTAI4FgTD7Te1pQgkcqLhrhJ";
    private static String accessKeySecret = "edurjr9VWDkDW7lvkSPWubyu1NaMhY";

    private static String bucket="yingx-ali";

    /**
    *   上传本地文件
    *   @param fileName:  指定上传文件名  可以指定上传目录：  目录名/文件名
    *   @param localFilePath: 指定本地文件路径
    * */
    public static void uploadFile(String fileName,String localFilePath){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, new File(localFilePath));

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     *   上传本地文件
     *   @param headImg: 指定MultipartFile类型的文件
     *   @param fileName:  指定上传文件名  可以指定上传目录：  目录名/文件名
     * */
    public static void uploadFileBytes(MultipartFile headImg, String fileName){

        //转为字节数组
        byte[] bytes = new byte[0];
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

        // 上传Byte数组。
        ossClient.putObject(bucket, fileName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     *删除阿里云文件
     * @param objectName:视频的名字
     * */

    public static void deltealiyun(String objectName){

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ossClient.deleteObject(bucket, objectName);

        ossClient.shutdown();
    }
    /**
     *截取视频的第一帧
     * @param fileName 视频的名字
     * @param coverName 视频封面的名字
     * */

    public static void intercptVideo(String fileName,String coverName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_50000,f_jpg,w_800,h_600";
        // 指定过期时间为10分钟。
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        //封面的网络路径
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);
        // 将视频的封面通过网络流上传到阿里云
        InputStream inputStream = null;
        try {
            inputStream = new URL(signedUrl.toString()).openStream();
            ossClient.putObject(bucket, coverName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
