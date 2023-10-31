package com.spotify.app.mapper;

import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.response.SongSearchResponse;
import com.spotify.app.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongSearchResponseMapper {
    SongSearchResponseMapper INSTANCE = Mappers.getMapper(SongSearchResponseMapper.class);

    SongSearchResponse songToDto(Song song);
    List<SongSearchResponse> songsToSongsDTO(List<Song> songs) ;
}
