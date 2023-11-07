package com.spotify.app.service;

import com.spotify.app.dto.UserDTO;
import com.spotify.app.dto.request.UserRequest;
import com.spotify.app.dto.response.UserFollowingsPlaylists;
import com.spotify.app.dto.response.*;
import com.spotify.app.enums.Gender;
import com.spotify.app.exception.DuplicateResourceException;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.*;
import com.spotify.app.model.*;
import com.spotify.app.repository.*;
import com.spotify.app.utility.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final int userPerPage = 10 ;
    //    private final S3Service s3Service;


    public UserDTO findByIdReturnRoleAndSongs(Long userId) {
        User user = userRepository.
                findByIdReturnRoleAndSongs(userId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("user with id: %d not found",userId))) ;

        return userMapper.userToUserDTO(user) ;
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

    public List<UserResponse> findAllArtistByUserName(String userName) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(0,5,sort);
        Page<User> usersPage = userRepository.findAllArtistByUserName(pageable, userName);
        return userResponseMapper.usersToUsersResponse(usersPage.getContent());
    }

    public UserResponse addUser(UserRequest request) {
        log.info(String.valueOf(request));
        if(checkUserExitByEmail(request.email())) {
            throw new DuplicateResourceException(String.format("email : [%s] is registered", request.email()));
        }
        // get role by roleName
        Role role = roleService.findByName(request.roleName());

        User underSave = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .dateOfBrith(LocalDateTime.of(request.year(),request.month(),request.day(),0,0))
                .password(passwordEncoder.encode(request.password()))
                .createdOn(LocalDateTime.now())
                .role(role)
                .build();
        User savedUser = userRepository.save(underSave);
        underSave.setGender(Gender.valueOf(request.gender()));

        // convert user to userResponseDTO
        UserResponse userResponseDTO=  userResponseMapper.userToUserResponse(savedUser) ;
        Playlist playlist = Playlist.builder()
                .name("Liked Songs")
                .build();
        playlist.addUser(savedUser);
        playlistRepository.save(playlist);
        return userResponseDTO;
    }

    public UserResponse updateUser(UserRequest request, Long userId) {
        User underUpdate = get(userId);

        if(checkUserExitByEmail(request.email()) && !underUpdate.getEmail().equals(request.email())) {
            throw new DuplicateResourceException(String.format("email : [%s] is existed", request.email()));
        }

        if(request.password() != null) {
            underUpdate.setPassword(passwordEncoder.encode(request.password()));
        }

        underUpdate.setFirstName(request.firstName());
        underUpdate.setLastName(request.lastName());
        underUpdate.setEmail(request.email());
        underUpdate.setDateOfBrith(LocalDateTime.of(request.year(),request.month(),request.day(),0,0));
        Role role = roleService.findByName(request.roleName());

        underUpdate.setRole(role);
        underUpdate.setGender(Gender.valueOf(request.gender()));

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
    public List<PlaylistResponse> addPlaylist(Long userId, Long playlistId) {
        User user = userRepository.findById(userId).orElseThrow();

        Playlist playlist = playlistService.get(playlistId);

        // Todo: check: Is user added playlist
        if(playlistUserService.checkUserIsAddedPlaylist(userId, playlistId)) {
            return playlistService.findByUserId(userId);
        }

        user.addPlaylist(playlist);
        userRepository.save(user);
        return playlistService.findByUserId(userId);
    }


    @Transactional
    public List<PlaylistResponse> removePlaylist(Long userId, Long playlistId) {
        if(!playlistUserService.checkUserIsAddedPlaylist(userId, playlistId)) {
            return playlistService.findByUserId(userId);
        }
        playlistUserService.deleteByUserAndPlaylist(userId, playlistId);

        return playlistService.findByUserId(userId);
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
                        songResponseMapper.songToSongResponse(song,null,null,null))
                .toList();
    }



    public void uploadPhoto(MultipartFile photo, Long userId) {
        User user = get(userId);
        if (!photo.isEmpty()) {
            String fileName = StringUtils.cleanPath(photo.getOriginalFilename());

            user.setPhoto(fileName);

            String uploadDir = "user-photos/" + userId;

            FileUploadUtil.cleanDir(uploadDir);
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, photo);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        } else {
            if (user.getPhoto().isEmpty()){
                user.setPhoto(null);
            }
        }
        userRepository.save(user);
    }

    public String updateStatus(Long userId) {
        User user = get(userId);
        user.setStatus(!user.isStatus());
        userRepository.saveAndFlush(user);
        String status = !user.isStatus() ? "disabled" : "enabled";
        return String.format("user with id: %d is ".concat(status),userId);
    }

    public LocalDateTime stringToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }

//    public void uploadPhoto(MultipartFile photo, Long userId) {
//        User user = get(userId);
//        if (!photo.isEmpty()) {
//            user.setPhoto(photo.getOriginalFilename());
//            try {
//                s3Service.putObject(
//                        String.format("user/photo/%d/%s",userId,photo.getOriginalFilename()),photo.getBytes());
//            } catch (IOException e) {
//                throw new RuntimeException(e.getMessage());
//            }
//            userRepository.save(user);
//        }
//    }


//    public byte[] getPhotoImage(Long userId) {
//        User underGet = get(userId);
//        if (underGet.getPhoto().isEmpty()) {
//            throw new ResourceNotFoundException(
//                    "user id :[%d] not found photo".formatted(userId));
//        }
//
//        byte[] playlistImage = s3Service.getObject(
//                "user/photo/%d/%s".formatted(userId, underGet.getPhoto())
//        );
//        return playlistImage;
//    }
}

