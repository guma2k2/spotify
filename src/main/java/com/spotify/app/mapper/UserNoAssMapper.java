package com.spotify.app.mapper;

import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.UserDTO;
import com.spotify.app.dto.response.UserResponseNoAssociation;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;


@Mapper(componentModel = "spring")
public interface UserNoAssMapper {
    UserNoAssMapper INSTANCE = Mappers.getMapper( UserNoAssMapper.class );

    UserResponseNoAssociation userToUserDTO(User user) ;


}
