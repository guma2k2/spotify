package com.spotify.app.controller;

import com.spotify.app.dto.UserDTO;
import com.spotify.app.model.User;
import com.spotify.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/viewPhoto/{userId}")
    public ResponseEntity<?> readImage(@PathVariable("userId") Long userId) {
        User user = userService.get(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/png"))
                .body(user.getPhoto());
    }
}
