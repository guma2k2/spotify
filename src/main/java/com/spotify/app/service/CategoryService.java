package com.spotify.app.service;

import com.spotify.app.dto.CategoryDTO;
import com.spotify.app.mapper.CategoryMapper;
import com.spotify.app.model.Category;
import com.spotify.app.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
