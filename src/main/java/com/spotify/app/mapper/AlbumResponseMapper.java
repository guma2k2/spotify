package com.spotify.app.mapper;

import com.spotify.app.dto.response.AlbumResponse;
import com.spotify.app.model.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AlbumResponseMapper {

    AlbumResponseMapper INSTANCE = Mappers.getMapper(AlbumResponseMapper.class);


    @Mapping(target = "releaseDate", expression = "java(getReleaseDate(album))")
    AlbumResponse albumToAlbumResponse(Album album);

    List<AlbumResponse> albumsToAlbumsResponse(List<Album> albums);

    default String getReleaseDate(Album album) {
        String pattern = "dd/MM/yyyy hh:mm:ss";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        return album.getReleaseDate().format(dateFormat) ;
    }
}
