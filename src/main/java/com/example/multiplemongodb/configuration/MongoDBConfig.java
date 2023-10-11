package com.example.multiplemongodb.configuration;

import lombok.Data;

@Data
public class MongoDBConfig {

    private String host;
    private int port;
    private String username;
    private String password;
    public String authenticationDatabase;
    private String database;

}
