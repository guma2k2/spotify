package com.spotify.app.mapper;

import com.spotify.app.dto.response.PlaylistResponse;
import com.spotify.app.model.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlaylistResponseMapper {
    PlaylistResponseMapper INSTANCE = Mappers.getMapper(PlaylistResponseMapper.class);
    PlaylistResponse playlistToPlaylistResponse(Playlist playlist) ;

    @Mapping(target = "sumSongCount", source = "sumSongCount")
    @Mapping(target = "totalTime", source = "totalTime")
    @Mapping(target = "likedCount", source = "likedCount")
    PlaylistResponse playlistToPlaylistResponseCustom(Playlist playlist,
                                                      int sumSongCount,
                                                      String totalTime,
                                                      long likedCount ) ;
}
