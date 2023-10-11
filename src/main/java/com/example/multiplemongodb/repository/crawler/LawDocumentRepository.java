package com.example.multiplemongodb.repository.crawler;

import com.example.multiplemongodb.entity.LawDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LawDocumentRepository extends MongoRepository<LawDocument, String> {
}
