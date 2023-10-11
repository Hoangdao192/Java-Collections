package com.example.multiplemongodb.configuration;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.example.multiplemongodb.repository.musicmanager",
        mongoTemplateRef = "musicManagerDbMongoTemplate"
)
public class MusicManagerMongoDbConfig {

    @Bean(name = "musicManagerDbConfig")
    @ConfigurationProperties(prefix = "application.database.mongodb.music-manager")
    public MongoDBConfig musicManagerDbConfig() {
        return new MongoDBConfig();
    }

    @Bean(name = "musicManagerDbClient")
    public MongoClient musicManagerDbClient(
            @Qualifier("musicManagerDbConfig") MongoDBConfig musicManagerDbConfig
    ) {
        MongoCredential credential = MongoCredential
                .createCredential(
                        musicManagerDbConfig.getUsername(),
                        musicManagerDbConfig.getAuthenticationDatabase(),
                        musicManagerDbConfig.getPassword().toCharArray()
                );

        ServerAddress serverAddress = new ServerAddress(
                musicManagerDbConfig.getHost(), musicManagerDbConfig.getPort()
        );
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder
                        .hosts(Collections.singletonList(serverAddress)))
                .credential(credential)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean(name = "musicManagerDbDatabaseFactory")
    public MongoDatabaseFactory musicManagerDbDatabaseFactory(
            @Qualifier("musicManagerDbConfig") MongoDBConfig musicManagerDbConfig,
            @Qualifier("musicManagerDbClient") MongoClient mongoClient
    ) {
        return new SimpleMongoClientDatabaseFactory(
                mongoClient, musicManagerDbConfig.getDatabase()
        );
    }

    @Bean(name = "musicManagerDbMongoTemplate")
    public MongoTemplate musicManagerDbMongoTemplate(
            @Qualifier("musicManagerDbDatabaseFactory") MongoDatabaseFactory mongoDatabaseFactory
    ) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

}
