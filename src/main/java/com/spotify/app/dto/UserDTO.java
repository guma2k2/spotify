package com.spotify.app.dto;


import com.spotify.app.enums.Gender;
import java.util.Set;

public record UserDTO (Long id,
                       String firstName,
                       String lastName,
                       String fullName,
                       String email,
                       Gender gender,
                       boolean status,
                       RoleDTO role,
                       String photoImagePath,
                       Set<SongDTO> songs
) {
}
