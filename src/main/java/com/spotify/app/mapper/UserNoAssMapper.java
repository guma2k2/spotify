package com.spotify.app.mapper;

import com.spotify.app.dto.response.UserNoAssociationResponse;
import com.spotify.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserNoAssMapper {
    UserNoAssMapper INSTANCE = Mappers.getMapper( UserNoAssMapper.class );

    UserNoAssociationResponse userToUserDTO(User user) ;


}
