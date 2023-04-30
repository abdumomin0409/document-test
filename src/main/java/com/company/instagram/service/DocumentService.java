package com.company.instagram.service;

import com.company.instagram.domains.Document;
import com.company.instagram.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.company.instagram.StringUtils.getExtension;
import static io.grpc.inprocess.InProcessServerBuilder.generateName;


@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public Document saveDocuments(MultipartFile file) throws IOException {
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        String generatedName = generateName() + extension;
        Document document = Document.childBuilder()
                .originalName(file.getOriginalFilename())
                .generatedName(generatedName)
                .extension(extension)
                .mimeType(file.getContentType())
                .size(file.getSize())
//                            .path(mediaService.upload(file, generatedName))
                .build();
        System.out.println(document);
        documentRepository.save(document);
        return document;
}


    public Optional<Document> getDocument(Long id) {
        return documentRepository.findById(id);
    }

}

