package com.spotify.app.service;

import com.spotify.app.dto.CategoryDTO;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.CategoryMapper;
import com.spotify.app.model.Category;
import com.spotify.app.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository ;


    public Set<CategoryDTO> listParent() {
        Set<Category> categories = categoryRepository.listAllParent();
        return CategoryMapper.INSTANCE.categoriesToCategoriesDTO(categories);
    }

    public Set<CategoryDTO> listByParentId(Integer parentId) {
        Set<Category> categories = categoryRepository.listAllChildByParenId(parentId);
        return CategoryMapper.INSTANCE.categoriesToCategoriesDTO(categories);
    }

    @Transactional
    public void uploadFiles(MultipartFile image, MultipartFile thumbnail, Integer cateId) {
        Category category = categoryRepository.findById(cateId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if (image != null) {
            try {
                category.setImage(image.getBytes());
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }
        if (thumbnail != null) {
            try {
                category.setThumbnail(thumbnail.getBytes());
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }
        categoryRepository.save(category);
    }

    public Category get(Integer cateId) {
        return categoryRepository.findById(cateId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    public Set<CategoryDTO>  findCategoryNameHome() {
        return listByParentId(1);
    }

    public CategoryDTO findByIdCustom(Integer id) {
        Category category  = categoryRepository.findByIdCustom(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(category);
    }
}
