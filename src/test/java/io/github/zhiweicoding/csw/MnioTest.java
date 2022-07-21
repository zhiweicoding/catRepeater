package io.github.zhiweicoding.csw;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;

import java.util.concurrent.TimeUnit;


/**
 * @author by diaozhiwei on 2022/05/29.
 * @email diaozhiwei2k@163.com
 */
public class MnioTest {

    public static void main(String[] args) throws Exception {
        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = MinioClient.builder().endpoint("http://127.0.0.1:9000")
                    .credentials("wd0MvYcp24kYeoVP", "gJtfvd4FET8JImBxacDqIiDMdgOEh6QO")
                    .build();

            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket("one").build());
            if (isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket("one")
                                .build());
            }

            // 使用putObject上传一个文件到存储桶中。
//            ObjectWriteResponse one = minioClient.uploadObject(
//                    UploadObjectArgs.builder()
//                            .bucket("one")
//                            .object("mylove3.jpeg")
//                            .filename("/Users/zhiwei/Pictures/wo/FPbY9NpUYAcrtl0.jpeg")
//                            .build());
            String json = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\": " +
                    "          [\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\"],\"Resource\": " +
                    "          [\"arn:aws:s3:::%s\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\": " +
                    "          [\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::%s/*\"]}]}";

            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder().bucket("one").config(String.format(json,"one","one")).build());
//            String url = minioClient.getPresignedObjectUrl(
//                    GetPresignedObjectUrlArgs.builder()
//                            .method(Method.GET)
//                            .bucket("one")
//                            .object("mylove3.jpeg")
//                            .build());
//            System.out.println(url);
//            System.out.println("/Users/zhiwei/minio/one/response_1653557431780.json is successfully uploaded as json to `one` bucket.");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}
