package com.spotify.app.dto.response;

import com.spotify.app.dto.RoleDTO;
import com.spotify.app.enums.Gender;

public record UserResponseDTO (Long id, String firstName, String lastName,String fullName, String email, Gender gender, String photoImagePath, RoleDTO role){
}
