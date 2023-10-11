package com.example.multiplemongodb.configuration;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.example.multiplemongodb.repository.crawler",
        mongoTemplateRef = "crawlerDbMongoTemplate"
)
public class CrawlerMongoDbConfig {

    @Primary
    @Bean(name = "crawlerDbConfig")
    @ConfigurationProperties(prefix = "application.database.mongodb.crawler")
    public MongoDBConfig crawlerDbConfig() {
        return new MongoDBConfig();
    }

    @Primary
    @Bean(name = "crawlerDbClient")
    public MongoClient crawlerDbClient(
            @Qualifier("crawlerDbConfig") MongoDBConfig crawlerDbConfig
    ) {
        MongoCredential credential = MongoCredential
                .createCredential(
                        crawlerDbConfig.getUsername(),
                        crawlerDbConfig.getAuthenticationDatabase(),
                        crawlerDbConfig.getPassword().toCharArray()
                );

        ServerAddress serverAddress = new ServerAddress(
                crawlerDbConfig.getHost(), crawlerDbConfig.getPort()
        );
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder
                        .hosts(Collections.singletonList(serverAddress)))
                .credential(credential)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Primary
    @Bean(name = "crawlerDbDatabaseFactory")
    public MongoDatabaseFactory crawlerDbDatabaseFactory(
            @Qualifier("crawlerDbConfig") MongoDBConfig crawlerDbConfig,
            @Qualifier("crawlerDbClient") MongoClient mongoClient
    ) {
        return new SimpleMongoClientDatabaseFactory(
                mongoClient, crawlerDbConfig.getDatabase()
        );
    }

    @Primary
    @Bean(name = "crawlerDbMongoTemplate")
    public MongoTemplate crawlerDbMongoTemplate(
            @Qualifier("crawlerDbDatabaseFactory") MongoDatabaseFactory mongoDatabaseFactory
    ) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

}
