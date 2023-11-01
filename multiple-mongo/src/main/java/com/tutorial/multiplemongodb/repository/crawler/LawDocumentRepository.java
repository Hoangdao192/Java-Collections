package com.tutorial.multiplemongodb.repository.crawler;

import com.tutorial.multiplemongodb.entity.LawDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LawDocumentRepository extends MongoRepository<LawDocument, String> {
}
