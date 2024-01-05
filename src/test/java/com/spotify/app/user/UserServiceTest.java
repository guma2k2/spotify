package com.spotify.app.user;


import com.spotify.app.dto.RoleDTO;
import com.spotify.app.dto.UserDTO;
import com.spotify.app.dto.request.UserRequest;
import com.spotify.app.dto.response.PlaylistResponse;
import com.spotify.app.dto.response.UserResponse;
import com.spotify.app.enums.Gender;
import com.spotify.app.exception.DuplicateResourceException;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.SongResponseMapper;
import com.spotify.app.mapper.UserMapper;
import com.spotify.app.mapper.UserResponseMapper;
import com.spotify.app.model.Playlist;
import com.spotify.app.model.Role;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.repository.UserRepository;
import com.spotify.app.service.*;
import com.spotify.app.utility.FileUploadUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserServiceTest {

    @Mock
    private  UserRepository userRepository ;
    @Mock
    private  PlaylistRepository playlistRepository;
    @Mock
    private  UserMapper userMapper;
    @Mock
    private  UserResponseMapper userResponseMapper;
    @Mock
    private  SongResponseMapper songResponseMapper;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  PlaylistUserService playlistUserService;
    @Mock
    private  FollowerService followerService;
    @Mock
    private  AlbumService albumService;
    @Mock
    private  PlaylistService playlistService;
    @Mock
    private  RoleService roleService;
    @Mock
    private  CloudinaryService cloudinaryService;

    private UserService underTest ;

    @BeforeEach
    public void setUp() {
        underTest = new UserService(
                userRepository,
                playlistRepository,
                userMapper,
                userResponseMapper,
                songResponseMapper,
                passwordEncoder,
                playlistUserService,
                followerService,
                albumService,
                playlistService,
                roleService,
                cloudinaryService
                ) ;
    }

    @Test
    public void canGetUser() {
        // given
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .email("thuann@gmail.com")
                .gender(Gender.MALE)
                .firstName("thuan")
                .lastName("ngo")
                .build();
        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        User actual = underTest.get(userId);

        // then
        assertThat(actual).isEqualTo(user);
    }


    @Test
    public void willThrowWhenGetCustomerReturnEmpty() {
        // given
        Long userId = 1L ;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest.get(userId)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void canGetRoleAndSongsByUser() {
        // given
        Long userId = 1L ;
        User user = User.builder()
                .id(userId)
                .email("thuann@gmail.com")
                .gender(Gender.MALE)
                .firstName("thuan")
                .lastName("ngo")
                .build();
        String roleName = "role_name";
        Role role = Role.builder().name(roleName).build();
        Song song = Song.builder().build();
        Set<Song> songs = new HashSet<>();
        songs.add(song);
        user.setRole(role);
        user.setSongs(songs);

        when(userRepository.findByIdReturnRoleAndSongs(userId)).thenReturn(Optional.of(user));
        UserDTO expected = userMapper.userToUserDTO(user);

        // when
        UserDTO actual = underTest.findByIdReturnRoleAndSongs(userId);
        // then
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void shouldReturnTrue__WhenCheckUserExitByEmail() {
        // given
        String email = "thuann@gmail.com";
        User user = User.builder()
                .id(1L)
                .email(email)
                .gender(Gender.MALE)
                .firstName("thuan")
                .lastName("ngo")
                .build();
        // when
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        // then
        assertThat(underTest.checkUserExitByEmail(email)).isTrue();
    }




    @Test
    public void shouldReturnFalse__WhenCheckUserExitByEmail() {
        // given
        String email = "thuann@gmail.com";
        // when
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        // then
        assertThat(underTest.checkUserExitByEmail(email)).isFalse();
    }

    @Test
    public void canCreatePlaylist() {
        // given
        String email = "thuann@gmail.com";
        Long userId = 1L ;
        Long playlistId = 1L;
        User user = User.builder()
                .id(userId)
                .email(email)
                .gender(Gender.MALE)
                .firstName("thuan")
                .lastName("ngo")
                .build();
        List<PlaylistResponse> expected = new ArrayList<>();
        Playlist beAddedPlaylist = Playlist
                .builder()
                .id(playlistId)
                .name("playlist will be added")
                .description("desc")
                .build();
        PlaylistResponse playlistResponse = new PlaylistResponse(
                playlistId,
                "playlist will be added",
                "desc",
                FileUploadUtil.baseUrlPlaylistImage,
                FileUploadUtil.baseUrlPlaylistThumbnail,
                0,
                null,
                0L);
        expected.add(playlistResponse);
        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(underTest.get(1L)).thenReturn(user);
        when(playlistService.get(playlistId)).thenReturn(beAddedPlaylist);
        when(playlistUserService.checkUserIsAddedPlaylist(userId,playlistId)).thenReturn(false);
        when(playlistService.findByUserId(userId)).thenReturn(expected);
        // then
        List<PlaylistResponse> actual = underTest.addPlaylist(userId,playlistId);
        assertThat(expected.size()).isEqualTo(actual.size());
    }

    @Test
    public void canAddUser(){
        // given
        String firstName = "firstname";
        String lastName = "lastname";
        String email = "email@gmail.com";
        String password = "passwords";
        String gender = "MALE";
        int day = 12;
        int month = 12;
        int year = 2002;
        String roleName = "ROLE_CUSTOMER";
        UserRequest userRequest = new UserRequest(firstName, lastName, email, password, gender, day, month, year, roleName);
        Role role =  Role.builder()
                .id(1)
                .name("ROLE_CUSTOMER")
                .build();
        User savedUser = User.builder()
                .id(999L)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .dateOfBrith(LocalDateTime.of(year, month, day,0,0))
                .password(passwordEncoder.encode(password))
                .createdOn(LocalDateTime.now())
                .gender(Gender.valueOf(gender))
                .role(role)
                .build();
        UserResponse expect = new UserResponse(999L, firstName, lastName, firstName+" " + lastName, email, Gender.valueOf(gender), "photo.png", true, "12/12/2002", new RoleDTO(1, "ROLE_CUSTOMER"));

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(roleService.findByName(Mockito.anyString())).thenReturn(role);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);
        Mockito.when(userResponseMapper.userToUserResponse(savedUser)).thenReturn(expect);

        // when
        UserResponse actual = underTest.addUser(userRequest);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(Mockito.anyString());
        Mockito.verify(roleService, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(playlistRepository, Mockito.times(1)).save(Mockito.any(Playlist.class));
        Mockito.verify(userResponseMapper, Mockito.times(1)).userToUserResponse(savedUser);

        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void cannotAddUserWithDuplicateEmail() {
        // given
        String firstName = "firstname";
        String lastName = "lastname";
        String email = "email@gmail.com";
        String password = "passwords";
        String gender = "MALE";
        int day = 12;
        int month = 12;
        int year = 2002;
        String roleName = "ROLE_CUSTOMER";
        UserRequest userRequest = new UserRequest(firstName, lastName, email, password, gender, day, month, year, roleName);
        Role role =  Role.builder()
                .id(1)
                .name("ROLE_CUSTOMER")
                .build();
        User existingUser = User.builder()
                .id(999L)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .dateOfBrith(LocalDateTime.of(year, month, day,0,0))
                .password(passwordEncoder.encode(password))
                .createdOn(LocalDateTime.now())
                .gender(Gender.valueOf(gender))
                .role(role)
                .build();;

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(existingUser));

        // when
        // then
        Assertions.assertThrows(DuplicateResourceException.class, () -> {
            underTest.addUser(userRequest);
        });

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(Mockito.anyString());
    }

    @Test
    public void canUpdateUser(){
        // given
        Long userId = 999L;
        String firstName = "firstname";
        String updatedFirstName = "updatedFirstname";
        String lastName = "lastname";
        String email = "email@gmail.com";
        String password = "passwords";
        String gender = "MALE";
        int day = 12;
        int month = 12;
        int year = 2002;
        String roleName = "ROLE_CUSTOMER";
        UserRequest userRequest = new UserRequest(updatedFirstName, lastName, email, password, gender, day, month, year, roleName);
        Role role =  Role.builder()
                .id(1)
                .name("ROLE_CUSTOMER")
                .build();
        User oldUser = User.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .dateOfBrith(LocalDateTime.of(year, month, day,0,0))
                .password(passwordEncoder.encode(password))
                .createdOn(LocalDateTime.now())
                .gender(Gender.valueOf(gender))
                .role(role)
                .build();

        User updatedUser = User.builder()
                .id(userId)
                .firstName(updatedFirstName)
                .lastName(lastName)
                .email(email)
                .dateOfBrith(LocalDateTime.of(year, month, day,0,0))
                .password(passwordEncoder.encode(password))
                .createdOn(LocalDateTime.now())
                .gender(Gender.valueOf(gender))
                .role(role)
                .build();
        UserResponse expect = new UserResponse(userId, firstName, lastName, firstName+" " + lastName, email, Gender.valueOf(gender), "photo.png", true, "12/12/2002", new RoleDTO(1, "ROLE_CUSTOMER"));

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(oldUser));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");
        Mockito.when(roleService.findByName(Mockito.anyString())).thenReturn(role);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(updatedUser);
        Mockito.when(userResponseMapper.userToUserResponse(Mockito.any())).thenReturn(expect);

        // when
        var actual = underTest.updateUser(userRequest, userId);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(Mockito.anyString());
        Mockito.verify(roleService, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(userResponseMapper, Mockito.times(1)).userToUserResponse(Mockito.any(User.class));
        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void cannotUpdateUserWithUserIdNotExist () {
        // given
        Long userId = 999L;
        String firstName = "firstname";
        String updatedFirstName = "updatedFirstname";
        String lastName = "lastname";
        String email = "email@gmail.com";
        String password = "passwords";
        String gender = "MALE";
        int day = 12;
        int month = 12;
        int year = 2002;
        String roleName = "ROLE_CUSTOMER";
        UserRequest userRequest = new UserRequest(updatedFirstName, lastName, email, password, gender, day, month, year, roleName);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           underTest.updateUser(userRequest, userId);
        });
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }


}
