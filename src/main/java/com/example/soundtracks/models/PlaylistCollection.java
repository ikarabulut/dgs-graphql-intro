package com.example.soundtracks.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class PlaylistCollection {
    List<MappedPlaylist> playlists;
    public void setPlaylists(JsonNode playlists) throws IOException {
        JsonNode playlistItems = playlists.get("items");

        ObjectMapper mapper = new ObjectMapper();
        mapper.readValue(playlistItems.traverse(), new TypeReference<List<MappedPlaylist>>(){});

        this.playlists = mapper.readValue(playlistItems.traverse(), new TypeReference<>(){});
    }

    public List<MappedPlaylist> getPlaylists() {
        return this.playlists;
    }
}
