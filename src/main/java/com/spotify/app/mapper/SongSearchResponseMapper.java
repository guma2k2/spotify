package com.spotify.app.mapper;

import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.response.SongSearchResponse;
import com.spotify.app.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongSearchResponseMapper {
    SongSearchResponseMapper INSTANCE = Mappers.getMapper(SongSearchResponseMapper.class);

    @Mapping(target = "duration" , expression = "java(getDuration(song))")
    SongSearchResponse songToDto(Song song);
    List<SongSearchResponse> songsToSongsDTO(List<Song> songs) ;


    default String getDuration(Song song) {
        int minute = song.getDuration()/60;
        int second = song.getDuration() - minute * 60 ;
        String secondString = second > 9  ? String.valueOf(second) : "0".concat(String.valueOf(second));
        return minute + ":" +secondString ;
    }
}
