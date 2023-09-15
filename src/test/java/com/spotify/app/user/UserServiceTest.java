package com.spotify.app.user;


import com.spotify.app.dto.UserDTO;
import com.spotify.app.mapper.UserMapper;
import com.spotify.app.model.User;
import com.spotify.app.repository.UserRepository;
import com.spotify.app.service.UserService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository ;


    private UserService underTest ;

    @BeforeEach
    public void setUp() {
        underTest = new UserService(userRepository) ;
    }

    @Test
    public void whenGetUserById_thenReturnSuccess() {
        Long userId = 2L;
        underTest.getUserById(userId) ;
        verify(userRepository).findByIdCustom(userId);
    }

    @Test
    public void whenMapUserToUserDTO_thenReturnSuccess() {
        User user = User.builder()
                .firstName("asdfa")
                .lastName("asdf")
                .build();
        UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);
        Assert.notNull(userDTO);
    }
}
