package com.spotify.app.mapper;


import com.spotify.app.dto.response.AlbumResponseDTO;
import com.spotify.app.dto.response.SongResponseDTO;
import com.spotify.app.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SongResponseMapper {


    SongResponseMapper INSTANCE = Mappers.getMapper(SongResponseMapper.class);

    @Mapping(target = "albums",source = "albumResponseDTOS")
    @Mapping(target = "createdAt", source = "createdOn")
    @Mapping(target = "duration" , expression = "java(getDuration(song))")
    @Mapping(target = "releaseDate", expression = "java(getReleaseDate(song))")
    SongResponseDTO songToSongResponseDTO(Song song,
                                          List<AlbumResponseDTO> albumResponseDTOS,
                                          String createdOn);

    default String getReleaseDate(Song song) {
        String pattern = "dd/MM/yyyy hh:mm:ss";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        return song.getReleaseDate().format(dateFormat) ;
    }
    default String getDuration(Song song) {
        int minute = song.getDuration()/60;
        int second = song.getDuration() - minute * 60 ;
        String secondString = second % 10 > 0 ? second+"" : "0" + second;
        return minute + ":" +secondString ;
    }
}
