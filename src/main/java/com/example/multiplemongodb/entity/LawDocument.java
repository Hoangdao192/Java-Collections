package com.example.multiplemongodb.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("thu_vien_phap_luat")
@Data
public class LawDocument {

    private String id;
    private String url;
    private String title;
    private String documentCode;
    private String documentType;
    private String issuedAgency;
    private String signerBy;
    private String issuedDate;
    private String effectiveDate;
    private String publicationCode;
    private String publicationDate;
    private String status;

}
