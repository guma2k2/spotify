package com.spotify.app.controller;

import com.spotify.app.dto.UserDTO;
import com.spotify.app.dto.response.UserFollowingsPlaylists;
import com.spotify.app.dto.response.PageResponse;
import com.spotify.app.dto.response.UserAlbumsSongs;
import com.spotify.app.dto.response.UserResponse;
import com.spotify.app.model.User;
import com.spotify.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService ;



    @GetMapping("/{userId}")
    public UserDTO getById(@PathVariable("userId") Long userId) {
        return userService.findByIdReturnRoleAndSongs(userId) ;
    }

    @PostMapping("/upload/image/{userId}")
    @Operation(description = "Save file image end with `png` or j only")
    public ResponseEntity<?> uploadPhoto(
            @RequestParam("image") MultipartFile photo,
            @PathVariable("userId") Long userId
    ) {
        userService.uploadPhoto(photo, userId);
        return ResponseEntity.ok().body("Save photo of user success");
    }

    @GetMapping
    public List<UserResponse> listAll() {
        return userService.listAll();
    }
    @GetMapping("/firstPage")
    public PageResponse listAllFirstPage() {
        return userService.getPageResponse(1,"desc", "id" , null);
    }

    @GetMapping("/pageable/")
    public PageResponse listAllPage(
            @RequestParam("numPage") int numPage,
            @RequestParam("sortDir") String sortDir,
            @RequestParam("sortField") String sortField,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        return userService.getPageResponse(numPage, sortDir, sortField, keyword);
    }

    @GetMapping("/admin/{userId}")
    public UserResponse findByUserIdForAdmin(
            @PathVariable("userId") Long userId
    ) {
        return userService.findByIdReturnWithRole(userId);
    }

    @PostMapping("/admin/save")
    public UserResponse addUser(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("roleName") String roleName,
                                @RequestParam("gender") String gender
    ) {
        return userService.addUser(firstName, lastName, email, password, roleName,gender);
    }


    @PutMapping("/admin/update/{userId}")
    public UserResponse updateUser(@RequestParam("firstName") String firstName,
                                   @RequestParam("lastName") String lastName,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("roleName") String roleName,
                                   @PathVariable("userId") Long userId,
                                   @RequestParam("gender") String gender
    ) {
        return userService.updateUser(firstName, lastName, email, password, roleName,userId,gender);
    }


    @GetMapping("/{userId}/playlists/followings")
    @Operation(description = "Find all followings and playlists by userId")
    public UserFollowingsPlaylists findByIdReturnFollowingsAndPlaylists(@PathVariable("userId") Long userId) {
        return userService.findByIdReturnFollowingsAndPlaylists(userId);
    }

    @GetMapping("/{userId}/add/{playlistId}")
    @Operation(description = "Click add button in playlist page to call this api")
    public ResponseEntity<?> addUserToLikedPlaylist(
            @PathVariable("playlistId") Long playlistId,
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok().body(userService.addPlaylist(userId, playlistId));
    }

    @GetMapping("/{userId}/remove/{playlistId}")
    @Operation(description = "Click add button in playlist page to call this api")
    public ResponseEntity<?> removeUserFromLikedPlaylist(
            @PathVariable("playlistId") Long playlistId,
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok().body(userService.removePlaylist(userId, playlistId));
    }


    @GetMapping("/{userId}/songs/albums")
    public UserAlbumsSongs findByIdReturnSongsAlbums(
            @PathVariable("userId") Long userId
    ) {
        return userService.findByIdReturnSongsAlbums(userId);
    }

    @GetMapping("/{userId}/is/liked/{songId}")
    public ResponseEntity<Boolean> checkCurrentUserIsLikedTargetSong(@PathVariable("userId") Long userId,
                                                                     @PathVariable("songId") Long songId) {
        boolean check = userService.checkCurrentUserIsLikedTargetSong(userId, songId);
        return ResponseEntity.ok().body(check) ;
    }
    @GetMapping("/find/artist/by/{userName}")
    public List<UserResponse> findAllArtistByUserName(
            @PathVariable("userName") String userName
    ) {
        return userService.findAllArtistByUserName(userName);
    }

    //////////////////////////////////////// S3 SERVICE/////////////////////////////////////

//    @GetMapping(
//            value = "/view/photo/{userId}",
//            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE}
//    )
//    public ResponseEntity<?> viewPhotoImage(@PathVariable("userId") Long userId) {
//        return ResponseEntity.ok()
//                .body(userService.getPhotoImage(userId));
//    }


}
