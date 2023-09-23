package com.spotify.app.dto.response;

import com.spotify.app.dto.RoleDTO;

public record UserResponseDTO (Long id, String firstName, String lastName, String email, String photoImagePath, RoleDTO role){
}
