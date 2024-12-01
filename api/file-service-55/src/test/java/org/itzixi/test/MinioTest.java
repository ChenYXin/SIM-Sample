package org.itzixi.test;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.junit.jupiter.api.Test;

public class MinioTest {

    @Test
    public void testUpload() throws Exception {
        //创建客户端
        MinioClient minioClient = MinioClient
                .builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("imooc", "imooc123456")
                .build();
        //如果没有bucket，则需要创建
        String bucketName = "localjava";
        boolean isExists = minioClient.bucketExists(
                BucketExistsArgs
                        .builder()
                        .bucket(bucketName)
                        .build());
        //判断当前的bucket是否存在，不存在则创建，存在则什么都不做
        if (!isExists) {
            minioClient.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(bucketName)
                            .build());
        } else {
            System.out.println("Bucket " + bucketName + " already exists");
        }
        //上传本地的文件到minio到服务中
        minioClient.uploadObject(UploadObjectArgs
                .builder()
                .bucket(bucketName)
                .object("myImage.png")
                .filename("/Users/chenyuexin/Downloads/Snipaste_2024-11-30_16-42-34.png")
                .build());

    }
}
