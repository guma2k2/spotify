package com.spotify.app.controller;

import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.UserDTO;
import com.spotify.app.dto.UserFollowingsPlaylists;
import com.spotify.app.dto.request.AlbumRequest;
import com.spotify.app.dto.response.UserResponseDTO;
import com.spotify.app.model.User;
import com.spotify.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        return userService.getUserById(userId) ;
    }

    @PostMapping("/uploadPhoto/{userId}")
    @Operation(description = "Save file image end with `png` only")
    public ResponseEntity<?> uploadPhoto(
            @RequestParam("photo") MultipartFile photo,
            @PathVariable("userId") Long userId
    ) {
        // Todo:
        userService.uploadPhoto(photo, userId);
        return ResponseEntity.ok().body("Save photo of user success");
    }
    @GetMapping(
            value = "/viewPhoto/{userId}",
            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> viewPhotoImage(@PathVariable("userId") Long userId) {
        User user = userService.get(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/png"))
                .body(user.getPhoto());
    }

    @GetMapping
    public List<UserResponseDTO> listAll() {
        return userService.listAll();
    }

    @GetMapping("/admin/{userId}")
    public UserResponseDTO listAll(
            @PathVariable("userId") Long userId
    ) {
        return userService.findByIdReturnWithRole(userId);
    }

    @PostMapping("/admin/save")
    public UserResponseDTO addUser(@RequestParam("firstName") String firstName,
                                   @RequestParam("lastName") String lastName,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam(value = "image",required = false) MultipartFile photoImage,
                                   @RequestParam("roleName") String roleName,
                                   @RequestParam("gender") String gender
    ) {
        return userService.addUser(firstName, lastName, email, password, photoImage, roleName,gender);
    }


    @PutMapping("/admin/update/{userId}")
    public UserResponseDTO updateUser(@RequestParam("firstName") String firstName,
                                      @RequestParam("lastName") String lastName,
                                      @RequestParam("email") String email,
                                      @RequestParam("password") String password,
                                      @RequestParam(value = "image",required = false) MultipartFile photoImage,
                                      @RequestParam("roleName") String roleName,
                                      @PathVariable("userId") Long userId,
                                      @RequestParam("gender") String gender
    ) {
        return userService.updateUser(firstName, lastName, email, password, photoImage, roleName,userId,gender);
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
        userService.addPlaylist(userId,playlistId);
        return ResponseEntity.ok().body(String.format("Add playlist %d by user %d successful",playlistId,userId));
    }

    @GetMapping("/{userId}/remove/{playlistId}")
    @Operation(description = "Click add button in playlist page to call this api")
    public ResponseEntity<?> removeUserFromLikedPlaylist(
            @PathVariable("playlistId") Long playlistId,
            @PathVariable("userId") Long userId
    ) {
        userService.removePlaylist(userId,playlistId);
        return ResponseEntity.ok().body(String.format("Remove playlist %d from user %d successful",playlistId,userId));
    }



    @PostMapping("/{userID}/add/album")
    public ResponseEntity<?> addUserToLikedPlaylist(
            @PathVariable("userID") Long userID,
            @Valid @RequestBody AlbumRequest request
    ) {
        Long albumId = userService.addAlbum(userID,request);
        return ResponseEntity.ok().body(albumId);
    }



}
