package com.spotify.app.role;

import com.spotify.app.dto.RoleDTO;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.RoleMapper;
import com.spotify.app.model.Role;
import com.spotify.app.repository.RoleRepository;
import com.spotify.app.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    private RoleService underTest;


    @Mock
    private  RoleRepository roleRepository ;

    @Mock
    private RoleMapper roleMapper;

    private Role role1 ;

    private Role role2 ;

    private RoleDTO roleDTO1;

    private RoleDTO roleDTO2;

    @BeforeEach
    void setUp () {
        underTest = new RoleService(roleRepository, roleMapper);

        role1 = Role.builder()
                .id(1)
                .name("ROLE_CUSTOMER")
                .build();

        role2 = Role.builder()
                .id(2)
                .name("ROLE_ADMIN")
                .build();
        roleDTO1 = new RoleDTO(1, "ROLE_CUSTOMER");
        roleDTO2 = new RoleDTO(2, "ROLE_ADMIN");

    }

    @Test
    public void canGetAllRole () {
        // given
        List<Role> roles = List.of(role1, role2);
        List<RoleDTO> expect = List.of(roleDTO1, roleDTO2);


        Mockito.when(roleRepository.findAll()).thenReturn(roles);
        Mockito.when(roleMapper.rolesToRolesDTO(Mockito.anyList())).thenReturn(expect);

        // when
        var actual = underTest.listAll();

        // then
        Mockito.verify(roleRepository, Mockito.times(1)).findAll();
        Mockito.verify(roleMapper, Mockito.times(1)).rolesToRolesDTO(Mockito.anyList());

        Assertions.assertEquals(actual, expect);
    }

    @Test
    public void canGetRoleByName () {
        // given
        String name = "ROLE_CUSTOMER";
        // when
        Mockito.when(roleRepository.findByName(Mockito.any())).thenReturn(Optional.of(role1));
        // then
        var actual = underTest.findByName(name);
        Assertions.assertEquals(actual, role1);
    }

    @Test
    public void cannotGetRoleByNameWithEmptyRole () {
        // given
        String name = "ROLE_CUSTOMER";
        Mockito.when(roleRepository.findByName(Mockito.any())).thenReturn(Optional.empty());

        // when
        // then
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            underTest.findByName(name);
        });
        Mockito.verify(roleRepository, Mockito.times(1)).findByName(Mockito.any());
    }
}
