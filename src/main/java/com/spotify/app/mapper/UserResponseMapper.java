package com.spotify.app.mapper;

import com.spotify.app.dto.RoleDTO;
import com.spotify.app.dto.response.UserResponseDTO;
import com.spotify.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponseMapper INSTANCE = Mappers.getMapper( UserResponseMapper.class );

    UserResponseDTO userToUserResponse(User user) ;

}
