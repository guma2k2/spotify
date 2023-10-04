package com.spotify.app.mapper;

import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.UserDTO;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;
import java.util.Set;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDTO userToUserDTO(User user) ;

    Set<SongDTO> songsToSongsDTO(Set<Song> songs) ;

    @Mapping(target = "duration" , expression = "java(getDuration(song))")
    @Mapping(target = "releaseDate", expression = "java(getReleaseDate(song))" , dateFormat = "dd/MM/yyyy hh:mm:ss")
    SongDTO songToSongDTO(Song song);

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
