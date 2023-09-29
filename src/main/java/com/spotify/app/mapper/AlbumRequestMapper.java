package com.spotify.app.mapper;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.request.AlbumRequest;
import com.spotify.app.model.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AlbumRequestMapper {

    AlbumRequestMapper INSTANCE = Mappers.getMapper( AlbumRequestMapper.class );

    Album dtoToEntity(AlbumRequest albumRequest);
}
