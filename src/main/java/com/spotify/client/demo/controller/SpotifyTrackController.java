package com.spotify.client.demo.controller;

import com.spotify.client.demo.dto.SpotifyTrackDto;
import com.spotify.client.demo.entity.Track;
import com.spotify.client.demo.model.Item;
import com.spotify.client.demo.model.SpotifyAlbum;
import com.spotify.client.demo.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class SpotifyTrackController {

    final static String APPLICATION_URL = "http://localhost:8080/";
    private TrackRepository trackRepository;

    @Autowired
    public SpotifyTrackController(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    /*
            Uses Spotify endpoint: https://api.spotify.com/v1/search, Http Method Get, OAuth: required
            Refer to: https://developer.spotify.com/console/ to explore more Spotify API
            offset - get next tracks
            limit - display more tracks on page (100 is limit)
     */
    @GetMapping("artist/{artist}")
    public List<SpotifyTrackDto> getAlbumByAuthor(OAuth2Authentication authentication,
                                                  @PathVariable String artist,
                                                  @RequestParam( value = "offset", defaultValue = "0") Optional<String> offset,
                                                  @RequestParam( value = "limit", defaultValue = "10") Optional<String> limit) {
        String jsonWebToken = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jsonWebToken);

        int offsetParam = Integer.parseInt(offset.orElse("0"));
        int limitParam = Integer.parseInt(limit.orElse("10"));

        final ResponseEntity<SpotifyAlbum> responseEntity = restTemplate.exchange(
                "https://api.spotify.com/v1/search?q=" +
                        artist +
                    "&type=track&market=US&limit=" +
                        limitParam +
                        "&offset=" +
                        offsetParam,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                SpotifyAlbum.class);

        Function<Item, String> imageUrlExtractor = item -> item.getAlbum().getImages().get(0).getUrl();
        Function<Item, String> albumExtractor = item -> item.getAlbum().getName();
        return responseEntity.getBody().getTracks().getItems().stream()
                .map(item -> {
                    try {
                        return new SpotifyTrackDto(
                                new Track(capitalize(artist), item.getName(), albumExtractor.apply(item)),
                                imageUrlExtractor.apply(item),
                                new URL(
                                APPLICATION_URL + "add-track/"
                                        + artist + "/"
                                        + item.getName() + "/"
                                        + albumExtractor.apply(item)));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/add-track")
    public Track saveTrackToDatabase(@RequestBody Track track) {
        return trackRepository.insert(track);
    }

    @GetMapping("/add-track/{artist}/{trackName}/{album}")
    public Track saveTrack(@PathVariable(value = "artist") String artist,
                          @PathVariable(value = "trackName") String trackName,
                          @PathVariable(value = "album") String album) {
        return trackRepository.insert(new Track(capitalize(artist), trackName, album));
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
