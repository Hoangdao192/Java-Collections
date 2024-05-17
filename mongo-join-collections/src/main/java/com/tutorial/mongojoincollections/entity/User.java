package com.tutorial.mongojoincollections.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.beans.Transient;

@Data
@Document
public class User {
    private String id;
    private String name;
}
