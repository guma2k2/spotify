package com.spotify.app.mapper;


import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.model.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    @Mapping(target = "songs", source = "songResponses")
    @Mapping(target = "sumSongCount", source = "sumSongCount")
    @Mapping(target = "totalTime", source = "totalTime")
    @Mapping(target = "likedCount", source = "likedCount")
    PlaylistDTO playlistToPlaylistDTO(Playlist playlist,int sumSongCount,String totalTime, long likedCount ,List<SongResponse> songResponses) ;
}
