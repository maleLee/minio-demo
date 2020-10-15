package com.yxh.lee.minio.controller;

import com.yxh.lee.minio.utils.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: minio-demo
 * @packageName: com.yxh.lee.minio.controller
 * @description:
 * @author: huayang.bai
 * @date: 2020/10/15 15:56
 */
@RestController
public class TestController {

    @Autowired
    private MinioUtils minioUtils;


    @GetMapping("/upload")
    public String upload() {
        String bucketName = "test-1";
        String filePath = "D:\\workspace\\learn\\minio-demo\\minio-demo.iml";
        String fileName = "minio";
        String contentType = ".iml";
        Boolean upload = MinioUtils.upload(bucketName, fileName, filePath, contentType);
        System.out.println(upload);
        return "";
    }

    @GetMapping("/download")
    public String download() {
        String bucketName = "test-1";
        String filePath = "D:\\workspace\\learn\\minio-demo\\111.iml";
        String fileName = "minio";
        MinioUtils.download(bucketName, fileName, filePath);
        return "";
    }

}
