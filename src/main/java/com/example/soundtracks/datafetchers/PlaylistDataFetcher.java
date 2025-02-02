package com.example.soundtracks.datafetchers;
import com.example.soundtracks.datasources.SpotifyClient;
import com.example.soundtracks.generated.types.AddItemsToPlaylistInput;
import com.example.soundtracks.generated.types.AddItemsToPlaylistPayload;
import com.example.soundtracks.generated.types.Playlist;
import com.example.soundtracks.generated.types.Track;
import com.example.soundtracks.models.MappedPlaylist;
import com.example.soundtracks.models.PlaylistCollection;
import com.example.soundtracks.models.Snapshot;
import com.netflix.graphql.dgs.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

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

    @DgsMutation
    public AddItemsToPlaylistPayload addItemsToPlaylist(@InputArgument AddItemsToPlaylistInput input) {
        String playlistId = input.getPlaylistId();
        List<String> uris = input.getUris();

        Snapshot snapshot = spotifyClient.addItemsToPlaylist(playlistId, String.join(",", uris));
        AddItemsToPlaylistPayload payload = new AddItemsToPlaylistPayload();
        if (snapshot != null) {
            String snapshotId = snapshot.id();
            if (Objects.equals(snapshotId, playlistId)) {
                Playlist playlist = new Playlist();
                playlist.setId(playlistId);

                payload.setCode(200);
                payload.setMessage("success");
                payload.setSuccess(true);
                payload.setPlaylist(playlist);

                return payload;
            }
        }
        payload.setCode(500);
        payload.setMessage("could not update playlist");
        payload.setSuccess(false);
        payload.setPlaylist(null);
        return payload;
    }

    @DgsData(parentType="AddItemsToPlaylistPayload", field="playlist")
    public MappedPlaylist getPayloadPlaylist(DgsDataFetchingEnvironment dfe) {
        AddItemsToPlaylistPayload payload = dfe.getSource();
        Playlist playlist = payload.getPlaylist();

        if (playlist != null) {
            String playlistId = playlist.getId();
            return spotifyClient.playlistRequest(playlistId);
        }

        return null;
    }
}


