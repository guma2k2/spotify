package com.spotify.app.mapper;

import com.spotify.app.dto.response.UserResponse;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponseMapper INSTANCE = Mappers.getMapper( UserResponseMapper.class );

    User dto2Entity(UserResponse userResponse);

    @Mapping(target = "dateOfBrith", expression = "java(getDateOfBirth(user))")
    UserResponse userToUserResponse(User user) ;

    List<UserResponse> usersToUsersResponse(List<User> users);

    default String getDateOfBirth(User user) {
        String pattern = "dd/MM/yyyy";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        return user.getDateOfBrith().format(dateFormat) ;
    }
}
