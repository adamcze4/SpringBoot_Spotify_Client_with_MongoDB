package com.spotify.client.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Track {

    @Id
    private String id;
    private String artist;
    private String trackName;
    private String album;

    public Track() {
    }

    public Track(String artist, String trackName, String album) {
        this.artist = artist;
        this.trackName = trackName;
        this.album = album;
    }

    public String getId() {
        return id;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
