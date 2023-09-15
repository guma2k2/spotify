package com.spotify.app.mapper;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.model.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AlbumMapper {
    AlbumMapper INSTANCE = Mappers.getMapper( AlbumMapper.class );
    @Mapping(target = "songs", source = "songDTOS")
    AlbumDTO albumToAlbumDTO(Album album, List<SongDTO> songDTOS);
}
