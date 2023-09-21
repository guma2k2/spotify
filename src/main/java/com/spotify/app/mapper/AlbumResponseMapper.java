package com.spotify.app.mapper;

import com.spotify.app.dto.response.AlbumResponseDTO;
import com.spotify.app.model.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AlbumResponseMapper {

    AlbumResponseMapper INSTANCE = Mappers.getMapper(AlbumResponseMapper.class);


    @Mapping(target = "releaseDate", expression = "java(getReleaseDate(album))" , dateFormat = "dd/MM/yyyy hh:mm:ss")
    AlbumResponseDTO albumToAlbumResponseDTO(Album album);

    List<AlbumResponseDTO> albumsToAlbumsResponseDTO(List<Album> albums);

    default LocalDateTime getReleaseDate(Album album) {
        return album.getReleaseDate();
    }
}
