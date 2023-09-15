package com.spotify.app.service;

import com.spotify.app.dto.RoleDTO;
import com.spotify.app.mapper.RoleMapper;
import com.spotify.app.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {


    private final RoleRepository roleRepository ;

    public List<RoleDTO> listAll() {
        return RoleMapper.INSTANCE.rolesToRolesDTO(roleRepository.findAll());
    }
}
