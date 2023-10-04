package com.spotify.app.mapper;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.response.SongResponse;
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

    @Mapping(target = "songs", source = "songResponses")
    @Mapping(target = "releaseDate", expression = "java(getReleaseDate(album))")
    @Mapping(target = "songCount", source = "songCount")
    @Mapping(target = "totalTime", source = "totalTime")
    AlbumDTO albumToAlbumDTO(Album album, List<SongResponse> songResponses, int songCount, String totalTime);


    Album dtoToEntity(AlbumDTO albumDTO);

    default String getReleaseDate(Album album) {
        String pattern = "dd/MM/yyyy hh:mm:ss";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        return album.getReleaseDate().format(dateFormat) ;
    }
}
