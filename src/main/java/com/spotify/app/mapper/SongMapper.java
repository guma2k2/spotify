package com.spotify.app.mapper;


import com.spotify.app.dto.SongDTO;
import com.spotify.app.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    @Mapping(target = "duration" , expression = "java(getDuration(song))")
    SongDTO songToSongDTO(Song song);

    List<SongDTO> songsToSongsDTO(List<Song> songs) ;

    default String getDuration(Song song) {
        int minute = song.getDuration()/60;
        int second = song.getDuration() - minute * 60 ;
        String secondString = second % 10 > 0 ? second+"" : "0" + second;
        return minute + ":" +secondString ;
    }

}
