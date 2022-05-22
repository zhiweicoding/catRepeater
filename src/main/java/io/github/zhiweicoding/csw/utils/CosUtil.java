package io.github.zhiweicoding.csw.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.retry.RetryPolicy;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Created by zhiwei on 2022/3/27.
 */
@Component
@Scope("singleton")
public class CosUtil {

    @Value("${cos.secretId:secretId}")
    private String secretId;
    @Value("${cos.secretKey:secretKey}")
    private String secretKey;
    @Value("${cos.region:region}")
    private String region;
    @Value("${cos.bucket:bucket}")
    private String bucket;
    @Value("${cos.baseUrl:baseUrl}")
    private String baseUrl;

    private COSClient cosClient;

    @PostConstruct
    public void init() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-shanghai");
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        clientConfig.setMaxErrorRetry(4);
//        clientConfig.setRetryPolicy(new OnlyIOExceptionRetryPolicy());
        cosClient = new COSClient(cred, clientConfig);
    }

    public void delCos(String fileKey) {
        if (fileKey != null && fileKey.contains("https://")) {
            fileKey = fileKey.replace(baseUrl, "");
        }
        if (cosClient == null) {
            init();
        }
        cosClient.deleteObject(bucket, fileKey);
    }

    public ObjectMetadata download(String cosFileName, String saveFileUrl) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, cosFileName);
        File downFile = new File(saveFileUrl);
        getObjectRequest = new GetObjectRequest(bucket, cosFileName);
        return cosClient.getObject(getObjectRequest, downFile);
    }

    public PutObjectResult upFile(String uploadFileName, InputStream io) {
        ObjectMetadata metadata = new ObjectMetadata();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadFileName, io, metadata);
        return cosClient.putObject(putObjectRequest);
    }

    public PutObjectResult upFile(String uploadFileName, File file) {
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        ObjectMetadata metadata = new ObjectMetadata();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadFileName, file);
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 自定义重试的策略
     * 如果是客户端的 IOException 异常则重试，否则不重试
     */
    public static class OnlyIOExceptionRetryPolicy extends RetryPolicy {
        @Override
        public <X extends CosServiceRequest> boolean shouldRetry(CosHttpRequest<X> request, HttpResponse response, Exception exception, int retryIndex) {
            //
            return exception.getCause() instanceof IOException;
        }
    }
}
