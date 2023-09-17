package com.spotify.app.mapper;

import com.spotify.app.dto.response.PlaylistResponseDTO;
import com.spotify.app.model.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PlaylistResponseMapper {
    PlaylistResponseMapper INSTANCE = Mappers.getMapper(PlaylistResponseMapper.class);

    PlaylistResponseDTO playlistToPlaylistResponseDTO(Playlist playlist) ;

    List<PlaylistResponseDTO> playlistsToPlaylistsDTO(List<Playlist> playlists) ;
}
