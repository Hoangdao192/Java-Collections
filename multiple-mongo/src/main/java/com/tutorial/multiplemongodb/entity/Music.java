package com.tutorial.multiplemongodb.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Music {
    private String id;
    private String name;
    private String author;

    public Music(String name, String author) {
        this.name = name;
        this.author = author;
    }
}
