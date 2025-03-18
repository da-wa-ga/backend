package dawaga.dawaga.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    // S3 파일 업로드
    public String uploadFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename(); // 중복 방지

        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return amazonS3.getUrl(bucketName, fileName).toString(); // 업로드된 파일 URL 반환
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    // **S3 파일 삭제**
    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return; // 삭제할 파일이 없는 경우 처리 X
        }

        // S3 버킷 내의 파일 이름 추출 (예: "https://bucket.s3.amazonaws.com/파일명")
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}
