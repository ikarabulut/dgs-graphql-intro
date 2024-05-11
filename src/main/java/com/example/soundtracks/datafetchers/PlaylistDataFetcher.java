package com.example.soundtracks.datafetchers;
import com.example.soundtracks.datasources.SpotifyClient;
import com.example.soundtracks.models.MappedPlaylist;
import com.example.soundtracks.models.PlaylistCollection;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DgsComponent
public class PlaylistDataFetcher {

    private final SpotifyClient spotifyClient;

    @Autowired
    public PlaylistDataFetcher(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }
    @DgsQuery
    public List<MappedPlaylist> featuredPlaylists() {
        PlaylistCollection response = spotifyClient.featuredPlaylistsRequest();
        return response.getPlaylists();
    }
}


