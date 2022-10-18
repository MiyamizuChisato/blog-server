package fun.ciallo.blog.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class OssUtils {
    @Value("${blog.endpoint}")
    private String ENDPOINT;
    @Value("${blog.access-key-id}")
    private String ACCESS_KEY_ID;
    @Value("${blog.access-key-secret}")
    private String ACCESS_KEY_SECRET;
    @Value("${blog.bucket-name}")
    private String BUCKET_NAME;

    public OSS getOSSClient() {
        return new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    public String upload(MultipartFile file, String folder) throws IOException {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf('.'));
        fileName = folder + "/" + fileName;
        OSS ossClient = getOSSClient();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setCacheControl("public");
        ossClient.putObject(BUCKET_NAME, fileName, file.getInputStream(), objectMetadata);
        file.getInputStream().close();
        return fileName;
    }

    public String uploadAvatar(MultipartFile file) throws IOException {
        return upload(file, "avatar");
    }
    public String uploadImage(MultipartFile file) throws IOException {
        return upload(file, "image");
    }

    public String uploadMarkdown(MultipartFile file) throws IOException {
        return upload(file, "markdown");
    }

    public void delete(String filename) {
        if (StringUtils.hasLength(filename)) {
            OSS ossClient = getOSSClient();
            if (ossClient.doesObjectExist(BUCKET_NAME, filename)) {
                ossClient.deleteObject(BUCKET_NAME, filename);
            }
            ossClient.shutdown();
        }
    }
}
