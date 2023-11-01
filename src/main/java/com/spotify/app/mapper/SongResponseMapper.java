package com.spotify.app.mapper;


import com.spotify.app.dto.response.AlbumResponse;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SongResponseMapper {


    SongResponseMapper INSTANCE = Mappers.getMapper(SongResponseMapper.class);

    @Mapping(target = "albums",source = "albumResponses")
    @Mapping(target = "createdAt", source = "createdOn")
    @Mapping(target = "duration" , expression = "java(getDuration(song))")
    @Mapping(target = "releaseDate", expression = "java(getReleaseDate(song))")
    SongResponse songToSongResponse(Song song, List<AlbumResponse> albumResponses, String createdOn);

    default String getReleaseDate(Song song) {
        String pattern = " MM/dd/yyyy hh:mm:ss";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        return song.getReleaseDate().format(dateFormat) ;
    }
    default String getDuration(Song song) {
        int minute = song.getDuration()/60;
        int second = song.getDuration() - minute * 60 ;
        String secondString = second > 9  ? String.valueOf(second) : "0".concat(String.valueOf(second));
        return minute + ":" +secondString ;
    }
}
