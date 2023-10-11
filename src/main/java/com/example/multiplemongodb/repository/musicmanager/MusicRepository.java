package com.example.multiplemongodb.repository.musicmanager;

import com.example.multiplemongodb.entity.Music;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicRepository extends MongoRepository<Music, String> {
}
