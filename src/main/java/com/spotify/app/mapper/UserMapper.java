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

import java.util.Set;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDTO userToUserDTO(User user) ;

    Set<SongDTO> songsToSongsDTO(Set<Song> songs) ;



}
