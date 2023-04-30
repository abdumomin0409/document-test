package com.company.instagram.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "childBuilder")
@ToString
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalName;

    private String generatedName;

    private String extension;

    private String mimeType;

    private Long size;

    private String path;
}
