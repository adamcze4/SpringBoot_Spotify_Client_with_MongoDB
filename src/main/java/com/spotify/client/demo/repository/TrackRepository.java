package com.spotify.client.demo.repository;

import com.spotify.client.demo.entity.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends MongoRepository<Track, String> {
}
