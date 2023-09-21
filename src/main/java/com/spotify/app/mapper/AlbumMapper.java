package com.spotify.app.mapper;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.model.Album;
import com.spotify.app.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AlbumMapper {
    AlbumMapper INSTANCE = Mappers.getMapper( AlbumMapper.class );
    @Mapping(target = "songs", source = "songDTOS")
    @Mapping(target = "releaseDate", expression = "java(getReleaseDate(album))" , dateFormat = "dd/MM/yyyy hh:mm:ss")
    AlbumDTO albumToAlbumDTO(Album album, List<SongDTO> songDTOS);

    default LocalDateTime getReleaseDate(Album album) {
        return album.getReleaseDate();
    }
}
