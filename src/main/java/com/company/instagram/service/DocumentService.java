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

    public List<Document> saveDocuments(List<MultipartFile> files) throws IOException {
        if (files.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        List<Document> documents = new ArrayList<>();

        files.forEach(
                file -> {
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
                    documentRepository.save(document);
                    documents.add(document);
                }
        );
        return documents;
    }


    public Optional<Document> getDocument(Long id) {
        return documentRepository.findById(id);
    }

}

