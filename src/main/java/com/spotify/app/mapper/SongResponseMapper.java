package com.spotify.app.mapper;


import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.SongResponseDTO;
import com.spotify.app.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SongResponseMapper {

    SongResponseMapper INSTANCE = Mappers.getMapper(SongResponseMapper.class);
    SongResponseDTO songToSongDTO(Song song);


}
