package com.spotify.app.service;

import com.spotify.app.dto.RoleDTO;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.RoleMapper;
import com.spotify.app.model.Role;
import com.spotify.app.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {


    private final RoleRepository roleRepository ;
    private final RoleMapper roleMapper ;

    public List<RoleDTO> listAll() {
        return roleMapper.rolesToRolesDTO(roleRepository.findAll());
    }

    public Role findByName(String name) {
       return roleRepository.
                findByName(name).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("role with name: [%s] not found",name))) ;
    }
}
