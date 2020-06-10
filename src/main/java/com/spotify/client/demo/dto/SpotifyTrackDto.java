package com.spotify.client.demo.dto;

import com.spotify.client.demo.entity.Track;

import java.net.URL;

public class SpotifyTrackDto {

    private Track track;
    private String imageUrl;
    private URL addToDatabaseUrl;

    public SpotifyTrackDto() {
    }

    public SpotifyTrackDto(Track track, String imageUrl, URL addToDatabaseUrl) {
        this.track = track;
        this.imageUrl = imageUrl;
        this.addToDatabaseUrl = addToDatabaseUrl;
    }

    public Track getTrack() {
        return track;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public URL getAddToDatabaseUrl() {
        return addToDatabaseUrl;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAddToDatabaseUrl(URL addToDatabaseUrl) {
        this.addToDatabaseUrl = addToDatabaseUrl;
    }
}
