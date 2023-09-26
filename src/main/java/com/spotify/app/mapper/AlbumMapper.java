package com.spotify.app.mapper;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.model.Album;
import com.spotify.app.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    AlbumMapper INSTANCE = Mappers.getMapper( AlbumMapper.class );

    @Mapping(target = "songs", source = "songDTOS")
    @Mapping(target = "releaseDate", expression = "java(getReleaseDate(album))")
    AlbumDTO albumToAlbumDTO(Album album, List<SongDTO> songDTOS);

    default String getReleaseDate(Album album) {
        String pattern = "dd/MM/yyyy hh:mm:ss";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        return album.getReleaseDate().format(dateFormat) ;
    }
}
