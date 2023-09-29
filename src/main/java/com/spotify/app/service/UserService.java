package com.spotify.app.service;

import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.UserDTO;
import com.spotify.app.dto.UserFollowingsPlaylists;
import com.spotify.app.dto.request.AlbumRequest;
import com.spotify.app.dto.response.PlaylistResponseDTO;
import com.spotify.app.dto.response.UserResponseDTO;
import com.spotify.app.dto.response.UserResponseNoAssociation;
import com.spotify.app.enums.Gender;
import com.spotify.app.exception.DuplicateResourceException;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.*;
import com.spotify.app.model.*;
import com.spotify.app.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository ;
    private final UserMapper userMapper;
    private final UserResponseMapper userResponseMapper;
    private final PlaylistResponseMapper playlistResponseMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository ;
    private final PlaylistRepository playlistRepository;
    private final PlaylistUserRepository playlistUserRepository;
    private final AlbumRequestMapper albumRequestMapper ;
    private final FollowerRepository followerRepository;

    private final UserNoAssMapper userNoAssMapper;
    public UserDTO getUserById(Long userId) {
        Optional<User> user = userRepository.findByIdReturnRoleAndSongs(userId) ;
        if(!user.isPresent()) {
            throw new ResourceNotFoundException("User not found") ;
        }
        return userMapper.userToUserDTO(user.get()) ;
    }

    public void uploadPhoto(MultipartFile photo, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")) ;
        try {
            user.setPhoto(photo.getBytes());
        } catch (IOException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

        userRepository.save(user);
    }

    public User get(Long userId) {
        return userRepository.
                findById(userId).
                orElseThrow(() -> new ResourceNotFoundException(String.format("user %d not found", userId)));
    }

    public UserResponseDTO findByIdReturnWithRole(Long userId) {
        User user = userRepository.
                findById(userId).
                orElseThrow(() -> new ResourceNotFoundException(String.format("user %d not found", userId)));
        return userResponseMapper.userToUserResponse(user);
    }

    public List<UserResponseDTO> listAll() {
        List<User> users = userRepository.findAllCustom();

        return users.stream().map(userResponseMapper::userToUserResponse).collect(Collectors.toList());
    }

    public UserResponseDTO addUser(String firstName,
                                   String lastName,
                                   String email,
                                   String password,
                                   MultipartFile photoImage,
                                   String roleName,
                                   String gender) {

        if(checkUserExitByEmail(email)) {
            throw new DuplicateResourceException(String.format("email : [%s] is existed", email));
        }

        Role role = roleRepository.
                findByName(roleName).
                orElseThrow(() -> new ResourceNotFoundException(String.format("[%s] not found",roleName))) ;

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();

        if(!photoImage.isEmpty()) {
            try {
                user.setPhoto(photoImage.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        user.setGender(Gender.valueOf(gender));
        return userResponseMapper.userToUserResponse(userRepository.save(user)) ;
    }

    public UserResponseDTO updateUser(String firstName,
                                      String lastName,
                                      String email,
                                      String password,
                                      MultipartFile photoImage,
                                      String roleName,
                                      Long userId,
                                      String gender
    ) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new ResourceNotFoundException(String.format("user with id : [%d] not found", userId));
        }

        if(checkUserExitByEmail(email) && !user.get().getEmail().equals(email)) {
            throw new DuplicateResourceException(String.format("email : [%s] is existed", email));
        }


        User checkedUser = user.get();
        checkedUser.setFirstName(firstName);
        checkedUser.setLastName(lastName);
        checkedUser.setEmail(email);

        Role role = roleRepository.
                findByName(roleName).
                orElseThrow(() -> new ResourceNotFoundException(String.format("[%s] not found",roleName))) ;

        checkedUser.setRole(role);
        checkedUser.setGender(Gender.valueOf(gender));
        if(password != null) {
            checkedUser.setPassword(passwordEncoder.encode(password));
        }

        if(photoImage != null) {
            try {
                checkedUser.setPhoto(photoImage.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return userResponseMapper.userToUserResponse(userRepository.save(checkedUser)) ;
    }


    private boolean checkUserExitByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }



    public UserFollowingsPlaylists findByIdReturnFollowingsAndPlaylists (Long userId) {
        List<Playlist> playlists = playlistUserRepository.findByUserid(userId).stream().map(playlistUser -> playlistUser.getPlaylist()).toList();

        List<PlaylistResponseDTO> playlistResponseDTOS = playlists.stream().map(playlist -> playlistResponseMapper.playlistToPlaylistResponseDTOCustom(playlist,0,0,0)).toList() ;

        List<User> followings = followerRepository.findFollowingListByUseId(userId).stream().map(follower -> follower.getFollowedUser()).toList();

        List<UserResponseNoAssociation> userResponseDTOS = followings.stream().map(user -> userNoAssMapper.userToUserDTO(user)).toList() ;

        return new UserFollowingsPlaylists(userResponseDTOS,playlistResponseDTOS);
    }

    @Transactional
    public void addPlaylist(Long userId, Long playlistId) {
        User user = userRepository.
                findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Playlist playlist = playlistRepository.
                findById(playlistId).
                orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        user.addPlaylist(playlist);
        userRepository.save(user);
    }

    @Transactional
    public void removePlaylist(Long userId, Long playlistId) {
        playlistUserRepository.deleteByUserAndPlaylist(userId,playlistId);
    }

    @Transactional
    public Long addAlbum(Long userId, AlbumRequest request) {
        User user = userRepository.
                findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Album album = albumRequestMapper.dtoToEntity(request);
        album.setReleaseDate(LocalDateTime.now());
        user.addAlbum(album);
        userRepository.saveAndFlush(user);
        return album.getId();
    }
}
