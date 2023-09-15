package com.spotify.app.controller;


import com.spotify.app.dto.CategoryDTO;
import com.spotify.app.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService ;

    @GetMapping("/getAllParent")
    public Set<CategoryDTO> listAllParent() {
        return categoryService.listParent();
    }

    @GetMapping("/getChildBy/{parentId}")
    public Set<CategoryDTO> listChildByParentId(
            @PathVariable("parentId") Integer parentId
    ) {
        return categoryService.listByParentId(parentId);
    }
}
