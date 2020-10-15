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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
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
     * @param []
     * @return void
     * @throws
     * @author Seven Lee
     * @description 初始化minioClient对象
     * @date 2020/10/13
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
     * @param [bucketName]
     * @return boolean
     * @throws
     * @author Seven Lee
     * @description 检测指定bucket是否存在
     * @date 2020/10/13
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
     * @param [bucketName]
     * @return void
     * @throws
     * @author Seven Lee
     * @description 创建新的bucket
     * @date 2020/10/13
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
     * @param []
     * @return java.util.List<io.minio.messages.Bucket>
     * @throws
     * @author Seven Lee
     * @description 获取所有的bucket
     * @date 2020/10/13
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
     * @param [bucketName]
     * @return java.util.Optional<io.minio.messages.Bucket>
     * @throws
     * @author Seven Lee
     * @description 根据bucket名称获取bucket
     * @date 2020/10/13
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
     * @param [bucketName]
     * @return void
     * @throws
     * @author Seven Lee
     * @description 根据bucket名称删除bucket
     * @date 2020/10/13
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
     * @param [bucketName, prefix(前缀), recursive(是否递归)]
     * @return java.util.List<io.minio.messages.Item>
     * @throws
     * @author Seven Lee
     * @description
     * @date 2020/10/13
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
     * @param [bucketName, fileName(文件名称), expires(失效时间)]
     * @return java.lang.String
     * @throws
     * @author Seven Lee
     * @description 获取文件链接
     * @date 2020/10/13
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
     * @param [bucketName, fileName(文件名称), response(响应)]
     * @return void
     * @throws
     * @author Seven Lee
     * @description 文件下载
     * @date 2020/10/13
     **/
    public static void download(String bucketName, String fileName, String filePath) {
        // 获取对象的元数据
        try {
            minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(bucketName)
                    .filename(filePath)
                    .object(fileName)
                    .build());
            /*final ObjectStat stat = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName)
                    .object(fileName).build());
            response.setContentType(stat.contentType());
            response.setCharacterEncoding(UTF8_ENCODING);
            response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_AND + URLEncoder.encode(fileName, UTF8_ENCODING));
            InputStream is = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName).build());
            IOUtils.copy(is, response.getOutputStream());
            is.close();*/
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
     * @param [bucketName, fileName(文件名)]
     * @return io.minio.ObjectStat
     * @throws
     * @author Seven Lee
     * @description 获取文件信息
     * @date 2020/10/13
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
     * @param [bucketName, fileName, inputStream, objectSize, partSize, contentType]
     * @return void
     * @throws
     * @author Seven Lee
     * @description 文件上传
     * @date 2020/10/15
     **/
    public static Boolean upload(String bucketName, String fileName, String filePath, String contentType) {

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
        ObjectWriteResponse objectWriteResponse = null;
        try {
            objectWriteResponse = minioClient.uploadObject(UploadObjectArgs.builder()
                    .contentType(contentType)
                    .bucket(bucketName)
                    .filename(filePath)
                    .object(fileName)
                    .build());
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
        String object = objectWriteResponse.object();
        if(object.equals(fileName)) {
            return true;
        }
        return false;
    }

}
