package com.example.soundtracks.datafetchers;
import com.example.soundtracks.datasources.SpotifyClient;
import com.example.soundtracks.generated.types.Track;
import com.example.soundtracks.models.MappedPlaylist;
import com.example.soundtracks.models.PlaylistCollection;
import com.netflix.graphql.dgs.*;
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

    @DgsQuery
    public MappedPlaylist playlist(@InputArgument String id) {
        return spotifyClient.playlistRequest(id);
    }

    @DgsData(parentType="Playlist")
    public List<Track> tracks(DgsDataFetchingEnvironment dfe) {
        MappedPlaylist playlist = dfe.getSource();
        String id = playlist.getId();
        List<Track> tracks = playlist.getTracks();

        if (tracks != null) {
            return tracks;
        } else {
            return spotifyClient.tracksRequest(id);
        }
    }
}


