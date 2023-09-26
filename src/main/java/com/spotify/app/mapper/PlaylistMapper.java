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
    PlaylistDTO playlistToPlaylistDTO(Playlist playlist, List<SongResponseDTO> songDTOs) ;
}
