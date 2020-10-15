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
        String filePath = "D:\\system\\Downloads\\software\\office2019\\cn_office_professional_plus_2019_x86_x64_dvd_5e5be643.iso";
        String fileName = "cn_office_professional_plus_2019_x86_x64_dvd_5e5be643";
        String contentType = ".iso";
        Boolean upload = MinioUtils.upload(bucketName, fileName, filePath, contentType);
        System.out.println(upload);
        return "";
    }

    @GetMapping("/d")
    public String download() {
        String bucketName = "test-1";
        String filePath = "D:\\system\\Downloads\\software\\office2019\\abc.iso";
        String fileName = "cn_office_professional_plus_2019_x86_x64_dvd_5e5be643.iso";
        MinioUtils.download(bucketName, fileName, filePath);
        return "";
    }

}
