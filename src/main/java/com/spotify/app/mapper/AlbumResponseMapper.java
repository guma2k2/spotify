package com.spotify.app.mapper;

import com.spotify.app.dto.response.AlbumResponseDTO;
import com.spotify.app.model.Album;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AlbumResponseMapper {

    AlbumResponseMapper INSTANCE = Mappers.getMapper(AlbumResponseMapper.class);

    AlbumResponseDTO albumToAlbumResponseDTO(Album album);

    List<AlbumResponseDTO> albumsToAlbumsResponseDTO(List<Album> albums);
}
