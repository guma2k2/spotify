package com.spotify.app.mapper;

import com.spotify.app.dto.response.UserResponse;
import com.spotify.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponseMapper INSTANCE = Mappers.getMapper( UserResponseMapper.class );

    User dto2Entity(UserResponse userResponse);

    UserResponse userToUserResponse(User user) ;

    List<UserResponse> usersToUsersResponse(List<User> users);

}
