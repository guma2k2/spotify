package com.spotify.app.dto;


import com.spotify.app.enums.Gender;
import java.util.Set;

public record UserDTO (Long id, String firstName, String lastName,String fullName, String email, Gender gender, RoleDTO role, String userPhotoPath, Set<SongDTO> songs) {
}
