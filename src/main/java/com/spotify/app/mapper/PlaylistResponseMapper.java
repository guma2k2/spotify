package com.spotify.app.mapper;

import com.spotify.app.dto.response.PlaylistResponseDTO;
import com.spotify.app.model.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaylistResponseMapper {
    PlaylistResponseMapper INSTANCE = Mappers.getMapper(PlaylistResponseMapper.class);
    PlaylistResponseDTO playlistToPlaylistResponseDTO(Playlist playlist) ;

    @Mapping(target = "sumSongCount", source = "sumSongCount")
    @Mapping(target = "sumViewCount", source = "sumViewCount")
    @Mapping(target = "likedCount", source = "likedCount")
    PlaylistResponseDTO playlistToPlaylistResponseDTOCustom(Playlist playlist,int sumSongCount, long sumViewCount, long likedCount ) ;
}
