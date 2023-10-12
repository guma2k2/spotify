package com.spotify.app.controller;


import com.spotify.app.dto.CategoryDTO;
import com.spotify.app.dto.response.CategoryResponse;
import com.spotify.app.model.Category;
import com.spotify.app.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    @PutMapping("/admin/update/{categoryId}")
    public ResponseEntity<?> updateCategory(
                        @PathVariable("categoryId") Integer categoryId,
                        @RequestParam("title") String title,
                        @RequestParam(value = "categoryParentTitle",required = false) String categoryParentTitle
    ){
        categoryService.updateCategory(categoryId,title ,categoryParentTitle);
        return ResponseEntity.ok().body(String.format("update category %d success", categoryId));
    }

    @PostMapping("/admin/save")
    @Operation(description = "Save file image end with `png` only")
    public ResponseEntity<?> addCategory(
            @RequestParam("title") String title,
            @RequestParam(value = "categoryParentTitle",required = false) String categoryParentTitle
    ){
        categoryService.addCategory(title, categoryParentTitle);
        return ResponseEntity.ok().body("save category success");
    }

    @PostMapping("/upload/image/{categoryId}")
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("categoryId") Integer catId
    )  {

        categoryService.saveCategoryImage(image, catId);
        return ResponseEntity.ok().body("Save image of category success");
    }

    @PostMapping("/upload/thumbnail/{categoryId}")
    public ResponseEntity<?> uploadThumbnail(
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @PathVariable("categoryId") Integer catId
    )  {

        categoryService.saveCategoryThumbnail(thumbnail, catId);
        return ResponseEntity.ok().body("Save thumbnail of category success");
    }

    
    @GetMapping("/admin/{categoryId}")
    public CategoryResponse findByIdForAdmin(@PathVariable("categoryId") Integer categoryId) {
        return categoryService.getByIdForAdmin(categoryId);
    }

    @GetMapping(
            value = "/viewImage/{categoryId}",
            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> viewImage(@PathVariable("categoryId") Integer categoryId) {
        return ResponseEntity.ok()
                .body(categoryService.getCategoryImage(categoryId));
    }


    @GetMapping(value = "/viewThumbnail/{categoryId}",
            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> viewThumbnail(@PathVariable("categoryId") Integer categoryId) {
        return ResponseEntity.ok()
                .body(categoryService.getCategoryThumbnail(categoryId));
    }

    @GetMapping("/{cateId}")
    public CategoryDTO getById(@PathVariable("cateId") Integer categoryId) {
        return categoryService.findByIdCustom(categoryId);
    }
    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryService.listAll();
    }

    @GetMapping("/admin/{categoryId}/add/{playlistId}")
    public ResponseEntity<String> addPlaylist(
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("playlistId") Long playlistId
    ) {
        categoryService.addPlaylist(categoryId,playlistId);
        return ResponseEntity.
                ok().
                body(String.
                        format("category with id: %d add playlist with id: %d success", categoryId, playlistId));
    }

    @GetMapping("/admin/{categoryId}/remove/{playlistId}")
    public ResponseEntity<String> removePlaylist(
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("playlistId") Long playlistId
    ) {
        categoryService.removePlaylist(categoryId,playlistId);
        return ResponseEntity.
                ok().
                body(String.
                        format("category with id: %d remove playlist with id: %d success", categoryId, playlistId));
    }
}
