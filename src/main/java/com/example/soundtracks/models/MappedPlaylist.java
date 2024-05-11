package com.example.soundtracks.models;

import com.example.soundtracks.generated.types.Playlist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MappedPlaylist extends Playlist {
    // custom logic will live here
}