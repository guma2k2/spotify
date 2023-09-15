package com.spotify.app.mapper;

import com.spotify.app.dto.RoleDTO;
import com.spotify.app.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO roleToRoleDTO(Role role);

    List<RoleDTO> rolesToRolesDTO(List<Role> roles) ;

}
