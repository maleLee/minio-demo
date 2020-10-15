package com.yxh.lee.minio.utils;

import com.yxh.lee.minio.properties.MinioProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.yxh.lee.minio.properties.MinioStatic.*;

/**
 * @Company YXH
 * @Author Seven Lee
 * @Date Create in 2020/10/15 11:04
 * @Description
 **/
@Component
public class MinioUtils {

    private MinioUtils() {

    }

    @Autowired
    private MinioProperties minioProperties;

    private static MinioClient minioClient;

    /**
     * @author Seven Lee
     * @description
     *      初始化minioClient对象
     * @param []
     * @date 2020/10/13
     * @return void
     * @throws
     **/
    @PostConstruct
    public void init() {
        try {
            minioClient = MinioClient.builder().endpoint(minioProperties.getUrl())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                    .build();
            createBucket(minioProperties.getBucketName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Seven Lee
     * @description
     *      检测指定bucket是否存在
     * @param [bucketName]
     * @date 2020/10/13
     * @return boolean
     * @throws
     **/
    public static boolean bucketExists(String bucketName) {
        BucketExistsArgs bucket = BucketExistsArgs.builder().bucket(bucketName).build();
        try {
            return minioClient.bucketExists(bucket);
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @author Seven Lee
     * @description
     *      创建新的bucket
     * @param [bucketName]
     * @date 2020/10/13
     * @return void
     * @throws
     **/
    public static void createBucket(String bucketName) {
        boolean isExist = bucketExists(bucketName);
        if (!isExist) {
            try {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } catch (ErrorResponseException e) {
                e.printStackTrace();
            } catch (InsufficientDataException e) {
                e.printStackTrace();
            } catch (InternalException e) {
                e.printStackTrace();
            } catch (InvalidBucketNameException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidResponseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (RegionConflictException e) {
                e.printStackTrace();
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (XmlParserException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @author Seven Lee
     * @description
     *      获取所有的bucket
     * @param []
     * @date 2020/10/13
     * @return java.util.List<io.minio.messages.Bucket>
     * @throws
     **/
    public static List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author Seven Lee
     * @description
     *      根据bucket名称获取bucket
     * @param [bucketName]
     * @date 2020/10/13
     * @return java.util.Optional<io.minio.messages.Bucket>
     * @throws
     **/
    public static Optional<Bucket> getBucket(String bucketName) {
        try {
            return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author Seven Lee
     * @description
     *      根据bucket名称删除bucket
     * @param [bucketName]
     * @date 2020/10/13
     * @return void
     * @throws
     **/
    public static void removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Seven Lee
     * @description
     * @param [bucketName, prefix(前缀), recursive(是否递归)]
     * @date 2020/10/13
     * @return java.util.List<io.minio.messages.Item>
     * @throws
     **/
    public static List<Item> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
        List<Item> objectList = new ArrayList<Item>();
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName)
                .prefix(prefix)
                .recursive(recursive).build());
        while (results.iterator().hasNext()) {
            try {
                objectList.add(results.iterator().next().get());
            } catch (ErrorResponseException e) {
                e.printStackTrace();
            } catch (InsufficientDataException e) {
                e.printStackTrace();
            } catch (InternalException e) {
                e.printStackTrace();
            } catch (InvalidBucketNameException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidResponseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (XmlParserException e) {
                e.printStackTrace();
            }
        }
        return objectList;
    }

    /**
     * @author Seven Lee
     * @description
     *      获取文件链接
     * @param [bucketName, fileName(文件名称), expires(失效时间)]
     * @date 2020/10/13
     * @return java.lang.String
     * @throws
     **/
    public static String getObjectURL(String bucketName, String fileName, Integer expires) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName)
                    .object(fileName)
                    .expiry(expires).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidExpiresRangeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author Seven Lee
     * @description
     *      文件下载
     * @param [bucketName, fileName(文件名称), response(响应)]
     * @date 2020/10/13
     * @return void
     * @throws
     **/
    public static void download(String bucketName, String fileName, HttpServletResponse response) {
        // 获取对象的元数据
        try {
            final ObjectStat stat = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName)
                    .object(fileName).build());
            response.setContentType(stat.contentType());
            response.setCharacterEncoding(UTF8_ENCODING);
            response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_AND + URLEncoder.encode(fileName, UTF8_ENCODING));
            InputStream is =minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName).build());
            IOUtils.copy(is, response.getOutputStream());
            is.close();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Seven Lee
     * @description
     *      获取文件信息
     * @param [bucketName, fileName(文件名)]
     * @date 2020/10/13
     * @return io.minio.ObjectStat
     * @throws
     **/
    public static ObjectStat getObjectInfo(String bucketName, String fileName) {
        try {
            return minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @author Seven Lee
     * @description
     *      文件上传
     * @param [bucketName, fileName, inputStream, objectSize, partSize]
     * @date 2020/10/13
     * @return void
     * @throws
     **/
    public static void upload(String bucketName, String fileName, InputStream inputStream, Long objectSize, Long partSize) {

        /**
         * 注意:
         *  <ul>
         *      <li>If object size is unknown, pass -1 to objectSize and pass valid partSize.
         *      <li>If object size is known, pass -1 to partSize for auto detect; else pass valid partSize
         *          to control memory usage and no. of parts in upload.
         *      <li>If partSize is greater than objectSize, objectSize is used as partSize.
         *  </ul>
         *  A valid part size is between 5MiB to 5GiB (both limits inclusive)
         */
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, objectSize, partSize).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
    }

}
