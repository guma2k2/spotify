package com.spotify.app.dto;


import lombok.Data;

import java.util.Set;

public record UserDTO (Long id, String firstName, String lastName, String email, RoleDTO role, Set<SongDTO> songs) {
}
