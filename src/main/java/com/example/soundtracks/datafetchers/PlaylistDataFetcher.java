package com.example.soundtracks.datafetchers;
import com.example.soundtracks.models.MappedPlaylist;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

import java.util.List;

@DgsComponent
public class PlaylistDataFetcher {

    @DgsQuery
    public List<MappedPlaylist> featuredPlaylists() {
        MappedPlaylist rockPlaylist = new MappedPlaylist();

        rockPlaylist.setId("1");
        rockPlaylist.setName("Rock n' Roll");
        rockPlaylist.setDescription("A rock n' roll playlist");

        MappedPlaylist popPlaylist = new MappedPlaylist();
        popPlaylist.setId("2");
        popPlaylist.setName("Pop");
        popPlaylist.setDescription("A pop playlist");


        return List.of(rockPlaylist, popPlaylist);
    }
}


