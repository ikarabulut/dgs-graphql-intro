package com.example.soundtracks.datasources;

import com.example.soundtracks.generated.types.Track;
import com.example.soundtracks.models.MappedPlaylist;
import com.example.soundtracks.models.PlaylistCollection;
import com.example.soundtracks.models.TrackCollection;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class SpotifyClient {
    private static final String SPOTIFY_API_URL = "https://spotify-demo-api-fe224840a08c.herokuapp.com/v1";
    private final RestClient client = RestClient.builder().baseUrl(SPOTIFY_API_URL).build();

    public PlaylistCollection featuredPlaylistsRequest() {
        return client
                .get()
                .uri("/browse/featured-playlists")
                .retrieve()
                .body(PlaylistCollection.class);
    }

    public MappedPlaylist playlistRequest(String playlistId) {
        return client
                .get()
                .uri("/playlists/{playlist_id}", playlistId)
                .retrieve()
                .body(MappedPlaylist.class);
    }

    public List<Track> tracksRequest(String playlistId) {
        var trackList = client
                .get()
                .uri("/playlists/{playlist_id}/tracks", playlistId)
                .retrieve()
                .body(TrackCollection.class);

        if (trackList != null) {
            return trackList.getTracks();
        } else {
            return null;
        }
    }
}