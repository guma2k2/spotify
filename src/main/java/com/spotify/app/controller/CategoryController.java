package com.spotify.app.controller;


import com.spotify.app.dto.CategoryDTO;
import com.spotify.app.model.Album;
import com.spotify.app.model.Category;
import com.spotify.app.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService ;

    @GetMapping("/getAllParent")
    @Operation(description = "Api list all category when clicked `search page`")
    public Set<CategoryDTO> listAllParent() {
        return categoryService.listParent();
    }

    @GetMapping("/getChildBy/{parentId}")
    @Operation(description = "Api get category child from `search page`")
    public Set<CategoryDTO> listChildByParentId(
            @PathVariable("parentId") Integer parentId
    ) {
        return categoryService.listByParentId(parentId);
    }

    @Operation(description = "Api get home's category")
    @GetMapping("/getCategoryHome")
    public Set<CategoryDTO> getCategoryHome(
    ) {
        return categoryService.findCategoryNameHome();
    }

    @PostMapping("/uploadFile/{cateId}")
    @Operation(description = "Save file image end with `png` only / save image and thumbnail")
    public ResponseEntity<?> uploadFiles( @RequestParam("image") MultipartFile image,
                                          @RequestParam("thumbnail") MultipartFile thumbnail,
                                          @PathVariable("cateId") Integer cateId
    ) {
        categoryService.uploadFiles(image,thumbnail,cateId);
        return ResponseEntity.ok().body(String.format("Upload files for category %d success",cateId));
    }

    @GetMapping("/viewImage/{cateId}")
    public ResponseEntity<?> viewImage(@PathVariable("cateId") Integer cateId) {
        Category category = categoryService.get(cateId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/png"))
                .body(category.getImage());
    }
    @GetMapping("/viewThumbnail/{cateId}")
    public ResponseEntity<?> viewThumbnail(@PathVariable("cateId") Integer cateId) {
        Category category = categoryService.get(cateId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/png"))
                .body(category.getThumbnail());
    }

    @GetMapping("/{cateId}")
    public CategoryDTO getById(@PathVariable("cateId") Integer categoryId) {
        return categoryService.findByIdCustom(categoryId);
    }
}
