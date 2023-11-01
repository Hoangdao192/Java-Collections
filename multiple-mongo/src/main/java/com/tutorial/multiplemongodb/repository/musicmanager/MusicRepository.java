package com.tutorial.multiplemongodb.repository.musicmanager;

import com.tutorial.multiplemongodb.entity.Music;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicRepository extends MongoRepository<Music, String> {
}
