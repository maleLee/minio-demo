package com.yxh.lee.minio.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Company YXH
 * @Author Seven Lee
 * @Date Create in 2020/10/15 11:03
 * @Description
 **/
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "spring.minio")
@Component
public class MinioProperties {

    private String url;
    private String accessKey;
    private String secretKey;
    private String bucketName;

}
