package com.tutorial.mongojoincollections.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Enterprise {
    private String id;
    private String name;
}
