//package com.spotify.app.user;
//
//
//import com.spotify.app.dto.UserDTO;
//import com.spotify.app.enums.Gender;
//import com.spotify.app.exception.ResourceNotFoundException;
//import com.spotify.app.mapper.UserMapper;
//import com.spotify.app.mapper.UserResponseMapper;
//import com.spotify.app.model.User;
//import com.spotify.app.repository.RoleRepository;
//import com.spotify.app.repository.UserRepository;
//import com.spotify.app.service.UserService;
//import io.jsonwebtoken.lang.Assert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.mockito.Mockito.when;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//
//    @Mock
//    private UserRepository userRepository ;
//    @Mock
//    private UserMapper userMapper ;
//    @Mock
//    private UserResponseMapper userResponseMapper ;
//
//    @Mock
//    private PasswordEncoder passwordEncoder ;
//
//    @Mock
//    private RoleRepository roleRepository ;
//
//    private UserService underTest ;
//
//
//
//    @BeforeEach
//    public void setUp() {
//        underTest = new UserService(userRepository,userMapper, userResponseMapper,passwordEncoder,roleRepository) ;
//    }
//
//    @Test
//    public void canGetUser() {
//        Long userId = 1L;
//        User user = User.builder()
//                .id(userId)
//                .email("thuann@gmail.com")
//                .gender(Gender.MALE)
//                .firstName("thuan")
//                .lastName("ngu")
//                .build();
//        when(userRepository.findByIdCustom(userId)).thenReturn(Optional.of(user));
//
//        underTest.getUserById(userId) ;
//
//
//        UserDTO expected = userMapper.userToUserDTO(user);
//
//        // when
//        UserDTO actual = underTest.getUserById(userId);
//
//        assertThat(actual).isEqualTo(expected);
//    }
//
//
//    @Test
//    public void willThrowWhenGetCustomerReturnEmpty() {
//        Long userId = 1L ;
//
//        when(userRepository.findByIdCustom(userId)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> underTest.getUserById(userId)).isInstanceOf(ResourceNotFoundException.class);
//    }
//
//
//}
