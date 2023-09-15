package com.spotify.app.mapper;

import com.spotify.app.dto.UserResponseDTO;
import com.spotify.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserResponseMapper {

    UserResponseMapper INSTANCE = Mappers.getMapper( UserResponseMapper.class );

    UserResponseDTO userToUserResponse(User user) ;
}
