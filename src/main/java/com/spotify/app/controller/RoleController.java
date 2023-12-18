package com.spotify.app.controller;

import com.spotify.app.dto.RoleDTO;
import com.spotify.app.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@AllArgsConstructor
public class RoleController {


    private final RoleService roleService ;

    @GetMapping
    @Operation(description = "List all role")
    public List<RoleDTO> listAll() {
        return roleService.listAll();
    }
}
