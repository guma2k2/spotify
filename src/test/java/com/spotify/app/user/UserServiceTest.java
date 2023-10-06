//package com.spotify.app.user;
//
//
//import com.spotify.app.dto.UserDTO;
//import com.spotify.app.enums.Gender;
//import com.spotify.app.exception.ResourceNotFoundException;
//import com.spotify.app.mapper.SongResponseMapper;
//import com.spotify.app.mapper.UserMapper;
//import com.spotify.app.mapper.UserResponseMapper;
//import com.spotify.app.model.Role;
//import com.spotify.app.model.Song;
//import com.spotify.app.model.User;
//import com.spotify.app.repository.PlaylistRepository;
//import com.spotify.app.repository.UserRepository;
//import com.spotify.app.service.*;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.mockito.Mockito.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//@ExtendWith(MockitoExtension.class)
//@Slf4j
//public class UserServiceTest {
//
//
//    @Mock
//    private  UserRepository userRepository ;
//    @Mock
//    private  PlaylistRepository playlistRepository;
//    @Mock
//    private  UserMapper userMapper;
//    @Mock
//    private  UserResponseMapper userResponseMapper;
//    @Mock
//    private  SongResponseMapper songResponseMapper;
//    @Mock
//    private  PasswordEncoder passwordEncoder;
//    @Mock
//    private  PlaylistUserService playlistUserService;
//    @Mock
//    private  FollowerService followerService;
//    @Mock
//    private  AlbumService albumService;
//    @Mock
//    private  PlaylistService playlistService;
//    @Mock
//    private  RoleService roleService;
//
//    private UserService underTest ;
//
//
//
//    @BeforeEach
//    public void setUp() {
//        underTest = new UserService(userRepository,
//                playlistRepository,
//                userMapper,
//                userResponseMapper,
//                songResponseMapper,
//                passwordEncoder,
//                playlistUserService,
//                followerService,
//                albumService,
//                playlistService,
//                roleService
//                ) ;
//    }
//
//    @Test
//    public void canGetUser() {
//        // given
//        Long userId = 1L;
//        User user = User.builder()
//                .id(userId)
//                .email("thuann@gmail.com")
//                .gender(Gender.MALE)
//                .firstName("thuan")
//                .lastName("ngo")
//                .build();
//        // when
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        User actual = underTest.get(userId);
//
//        // then
//        assertThat(actual).isEqualTo(user);
//    }
//
//
//    @Test
//    public void willThrowWhenGetCustomerReturnEmpty() {
//        // given
//        Long userId = 1L ;
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // when
//        // then
//        assertThatThrownBy(() -> underTest.get(userId)).isInstanceOf(ResourceNotFoundException.class);
//    }
//
//    @Test
//    public void canGetRoleAndSongsByUser() {
//        // given
//        Long userId = 1L ;
//        User user = User.builder()
//                .id(userId)
//                .email("thuann@gmail.com")
//                .gender(Gender.MALE)
//                .firstName("thuan")
//                .lastName("ngo")
//                .build();
//        String roleName = "role_name";
//        Role role = Role.builder().name(roleName).build();
//        Song song = Song.builder().build();
//        Set<Song> songs = new HashSet<>();
//        songs.add(song);
//        user.setRole(role);
//        user.setSongs(songs);
//
//        when(userRepository.findByIdReturnRoleAndSongs(userId)).thenReturn(Optional.of(user));
//        UserDTO expected = userMapper.userToUserDTO(user);
//
//        // when
//        UserDTO actual = underTest.findByIdReturnRoleAndSongs(userId);
//        // then
//        assertThat(actual).isEqualTo(expected);
//    }
//
//    @Test
//    public void canAddUser(){
//        // given
//        String email ="example.@gmail.com";
//        String password = "password";
//        String firstName = "firstName";
//        String lastName = "lastName";
//        String gender = "MALE";
//        String encodedPassword = "aa1235..//-*/+";
//
//        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
//
//        // when
//        underTest.addUser(firstName,
//                lastName,
//                email,
//                password,
//                any(),
//                "ROLE_CUSTOMER",gender);
//        // then
//        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//
//        verify(userRepository).save(userArgumentCaptor.capture());
//
//        User capturedUser = userArgumentCaptor.getValue();
//        assertThat(capturedUser.getId()).isNull();
//        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);
//    }
//
//    public void canUpdateUser(){
//        // Todo: update user
//    }
//
//
//}
