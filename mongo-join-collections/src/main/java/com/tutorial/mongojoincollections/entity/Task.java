package com.tutorial.mongojoincollections.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
public class Task {
    private String id;
    private String enterpriseId;
    private String assignerId;
    private String assigneeId;
    private String name;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @ReadOnlyProperty
    private Enterprise enterprise;
    @ReadOnlyProperty
    private User assigner;
    @ReadOnlyProperty
    private User assignee;
}
