package com.company.instagram.repository;

import com.company.instagram.domains.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("select d from Document d where d.path = ?1")
    Optional<Document> findByPath(String filePath);

    @Query("select d from Document d where d.generatedName = ?1")
    Optional<Document> findByName(String fileName);
    @Query("select d from Document d where d.generatedName = ?1")
    Document findByNameLink(String fileName);
}