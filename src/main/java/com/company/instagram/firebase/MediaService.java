package com.company.instagram.firebase;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static com.company.instagram.firebase.MediaUtils.*;



@Service
public class MediaService {
    @SneakyThrows
    public String upload(MultipartFile file, String generatedName) {
        StorageOptions
                .newBuilder()
                .setCredentials(getCredentials())
                .build()
                .getService()
                .create(getBlobInfo(generatedName, file.getContentType()), file.getBytes());
        return String.format(DOWNLOAD_URL, generatedName);
    }

    private BlobInfo getBlobInfo(String fileName, String contentType) {
        return BlobInfo
                .newBuilder(BlobId.of(BUCKET_NAME, fileName))
                .setContentType(Objects.requireNonNullElse(contentType, "media"))
                .build();
    }

    @SneakyThrows
    private Credentials getCredentials() {
        File file = new File(FIREBASE_CREDENTIALS_PATH);
        return GoogleCredentials.fromStream(new FileInputStream(file));
    }

//    public boolean deleteFile(String fileUniqueId) {
//        return StorageOptions
//                .newBuilder()
//                .setCredentials(getCredentials())
//                .build()
//                .getService()
//                .delete(BlobId.of(BUCKET_NAME, fileUniqueId));
//    }

    public File download(String fileName) throws IOException {
        Blob blob = StorageOptions
                .newBuilder()
                .setCredentials(getCredentials())
                .build()
                .getService()
                .get(BlobId.of(BUCKET_NAME, fileName));
        File file = File.createTempFile(fileName, ".tmp");
        blob.downloadTo(Paths.get(file.getAbsolutePath()));
        return file;
    }

}
