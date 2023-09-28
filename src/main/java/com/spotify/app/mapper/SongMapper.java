package com.spotify.app.mapper;


import com.spotify.app.dto.SongDTO;
import com.spotify.app.model.Song;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    @Mapping(target = "duration" , expression = "java(getDuration(song))")
    @Mapping(target = "releaseDate", expression = "java(getReleaseDate(song))" , dateFormat = "dd/MM/yyyy hh:mm:ss")
    SongDTO songToSongDTO(Song song);

    List<SongDTO> songsToSongsDTO(List<Song> songs) ;

    default String getDuration(Song song) {
        int minute = song.getDuration()/60;
        int second = song.getDuration() - minute * 60 ;
        String secondString = second > 9  ? String.valueOf(second) : "0".concat(String.valueOf(second));
        System.out.println(second);
        return minute + ":" +secondString ;
    }
    default String getReleaseDate(Song song) {
        String pattern = "dd/MM/yyyy hh:mm:ss";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        return song.getReleaseDate().format(dateFormat) ;
    }

}
