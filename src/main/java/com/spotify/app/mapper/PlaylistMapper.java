package com.spotify.app.mapper;


import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.response.SongResponseDTO;
import com.spotify.app.model.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    @Mapping(target = "songs", source = "songDTOs")
    @Mapping(target = "sumSongCount", source = "sumSongCount")
    @Mapping(target = "sumViewCount", source = "sumViewCount")
    @Mapping(target = "likedCount", source = "likedCount")
    PlaylistDTO playlistToPlaylistDTO(Playlist playlist,int sumSongCount,long sumViewCount, long likedCount ,List<SongResponseDTO> songDTOs) ;
}
