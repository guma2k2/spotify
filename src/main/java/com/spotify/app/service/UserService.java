package com.spotify.app.service;

import com.spotify.app.dto.UserDTO;
import com.spotify.app.dto.response.UserFollowingsPlaylists;
import com.spotify.app.dto.response.*;
import com.spotify.app.enums.Gender;
import com.spotify.app.exception.DuplicateResourceException;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.*;
import com.spotify.app.model.*;
import com.spotify.app.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository ;
    private final PlaylistRepository playlistRepository;
    private final UserMapper userMapper;
    private final UserResponseMapper userResponseMapper;
    private final SongResponseMapper songResponseMapper;
    private final PasswordEncoder passwordEncoder;
    private final PlaylistUserService playlistUserService;
    private final FollowerService followerService;
    private final AlbumService albumService;
    private final PlaylistService playlistService;
    private final RoleService roleService;

    private final PlaylistSongService playlistSongService;
    private final int userPerPage = 10 ;




    public boolean checkCurrentUserIsLikedTargetSong(Long userId, Long songId){
        // find playlist by name and userId
        Playlist playlist = playlistService.findByNameAndUserId(userId);

        // find song by playlist and song
        Song song =  playlistSongService.findByPlaylistIdAndSongId(playlist.getId(),songId);

        return song != null;
    }


    public UserDTO findByIdReturnRoleAndSongs(Long userId) {
        User user = userRepository.
                findByIdReturnRoleAndSongs(userId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("user with id: %d not found",userId))) ;

        return userMapper.userToUserDTO(user) ;
    }

    public void uploadPhoto(MultipartFile photo, Long userId) {
        User user = get(userId);
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

    public UserResponse findByIdReturnWithRole(Long userId) {
        User user = get(userId);
        return userResponseMapper.userToUserResponse(user);
    }

    public List<UserResponse> listAll() {
        // Todo: use pageable
        List<User> users = userRepository.findAllCustom();

        return users.stream().map(userResponseMapper::userToUserResponse).collect(Collectors.toList());
    }

    public Page<User> getUserPerPage(int numPage, String sortDir, String sortField, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(numPage - 1, userPerPage, sort);
        if(keyword != null) {
            return userRepository.findAllWithKeyword(keyword, pageable);
        }
        return userRepository.findAll(pageable);
    }

    public PageResponse<UserResponse> getPageResponse(int numPage, String sortDir, String sortField, String keyword) {
        Page<User> usersPage = getUserPerPage(numPage,sortDir,sortField,keyword);

        List<User> usersFromUserPage = usersPage.getContent();

        List<UserResponse> users = userResponseMapper.usersToUsersResponse(usersFromUserPage);

        int totalPage = usersPage.getTotalPages();

        return new PageResponse(totalPage,numPage,sortDir,sortField, users);
    }

    public UserResponse addUser(String firstName,
                                String lastName,
                                String email,
                                String password,
                                MultipartFile photoImage,
                                String roleName,
                                String gender) {

        if(checkUserExitByEmail(email)) {
            throw new DuplicateResourceException(String.format("email : [%s] is registered", email));
        }
        // get role by roleName
        Role role = roleService.findByName(roleName);

        User underSave = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        User savedUser = userRepository.save(underSave);
        saveUserPhotoImage(savedUser, photoImage);
        underSave.setGender(Gender.valueOf(gender));


        // convert user to userResponseDTO
        UserResponse userResponseDTO=  userResponseMapper.userToUserResponse(savedUser) ;
        Playlist playlist = Playlist.builder()
                .name("Liked Songs")
                .build();
        playlist.addUser(savedUser);
        playlistRepository.save(playlist);
        return userResponseDTO;
    }

    public UserResponse updateUser(String firstName,
                                   String lastName,
                                   String email,
                                   String password,
                                   MultipartFile photoImage,
                                   String roleName,
                                   Long userId,
                                   String gender
    ) {
        User underUpdate = get(userId);

        if(checkUserExitByEmail(email) && !underUpdate.getEmail().equals(email)) {
            throw new DuplicateResourceException(String.format("email : [%s] is existed", email));
        }

        if(password != null) {
            underUpdate.setPassword(passwordEncoder.encode(password));
        }

        saveUserPhotoImage(underUpdate, photoImage);
        underUpdate.setFirstName(firstName);
        underUpdate.setLastName(lastName);
        underUpdate.setEmail(email);

        Role role = roleService.findByName(roleName);

        underUpdate.setRole(role);
        underUpdate.setGender(Gender.valueOf(gender));

        return userResponseMapper.userToUserResponse(userRepository.save(underUpdate)) ;
    }


    public boolean checkUserExitByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }



    public UserFollowingsPlaylists findByIdReturnFollowingsAndPlaylists (Long userId) {
        // get playlist by userId
        List<PlaylistResponse> playlistResponses = playlistUserService.findByUserId(userId);

        // find all user who were followed by `userId`
        List<UserNoAssociationResponse> userResponseDTOS = followerService.findAllFollowingsByUserId(userId);

        return new UserFollowingsPlaylists(userResponseDTOS, playlistResponses);
    }

    @Transactional
    public String addPlaylist(Long userId, Long playlistId) {

        User user = get(userId);

        Playlist playlist = playlistService.get(playlistId);

        // Todo: check: Is user added playlist
        if(playlistUserService.checkUserIsAddedPlaylist(userId, playlistId)) {
            return String.format("user %d added playlist %d", userId, playlistId);
        }

        user.addPlaylist(playlist);
        userRepository.save(user);
        return String.format("Add playlist %d by user %d successful",playlistId, userId);
    }


    @Transactional
    public String removePlaylist(Long userId, Long playlistId) {
        if(playlistUserService.checkUserIsAddedPlaylist(userId, playlistId)) {
            return String.format("user %d already deleted playlist %d", userId, playlistId);
        }
        playlistUserService.deleteByUserAndPlaylist(userId, playlistId);

        return String.format("Remove playlist %d from user %d successful",playlistId,userId);
    }


    public UserAlbumsSongs findByIdReturnSongsAlbums(Long userId) {
        // get albums by user
        List<AlbumResponse> albums = albumService.findAlbumByUserId(userId);

        // get songs by user
        User user = userRepository.findByIdReturnRoleAndSongs(userId).orElseThrow() ;

        // convert songs to songResponses
        List<SongResponse> songResponses = convertSongsToSongResponses(user.getSongs());

        return new UserAlbumsSongs(albums, songResponses);
    }

    private List<SongResponse> convertSongsToSongResponses(Set<Song> songs) {
        return songs.stream()
                .map(song ->
                        songResponseMapper.songToSongResponse(song,null,null))
                .toList();
    }

    public void saveUserPhotoImage(User underSave, MultipartFile photoImage) {
        if(photoImage != null) {
            try {
                underSave.setPhoto(photoImage.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

}

