package com.company.instagram.controller;

import com.company.instagram.domains.Document;
import com.company.instagram.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/document")
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(
                documentService.getDocument(id).orElseThrow(
                        () -> new RuntimeException("Document not found")
                )
        );
    }

    @PostMapping(name = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Document>> uploadDocs(@RequestPart("files") List<MultipartFile> files) throws IOException {
        List<Document> documents = documentService.saveDocuments(files);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/get/{name:.*}")
    public ResponseEntity<Document> downloadFileURI(@PathVariable(name = "name") String name) throws IOException, URISyntaxException {
        documentService.downloadFile(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/download/{name:.*}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String name) throws IOException {
        File file = documentService.downloadFile(name);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}

