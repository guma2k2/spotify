package com.spotify.app.service;

import com.spotify.app.dto.CategoryDTO;
import com.spotify.app.dto.response.CategoryResponseDTO;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.CategoryMapper;
import com.spotify.app.mapper.CategoryResponseMapper;
import com.spotify.app.model.Category;
import com.spotify.app.model.Playlist;
import com.spotify.app.repository.CategoryRepository;
import com.spotify.app.repository.PlaylistRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository ;

    private final CategoryResponseMapper categoryResponseMapper;

    private final CategoryMapper categoryMapper;

    private final PlaylistRepository playlistRepository;

    public Set<CategoryDTO> listParent() {
        Set<Category> categories = categoryRepository.listAllParent();
        return CategoryMapper.INSTANCE.categoriesToCategoriesDTO(categories);
    }

    public Set<CategoryDTO> listByParentId(Integer parentId) {
        Set<Category> categories = categoryRepository.listAllChildByParenId(parentId);
        return CategoryMapper.INSTANCE.categoriesToCategoriesDTO(categories);
    }



    public Category get(Integer cateId) {
        return categoryRepository.findById(cateId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    public Set<CategoryDTO>  findCategoryNameHome() {
        return listByParentId(1);
    }

    public CategoryDTO findByIdCustom(Integer id) {
        Category category  = categoryRepository.
                findByIdCustom(id).
                orElseThrow(() -> new ResourceNotFoundException("category not found"));
        return categoryMapper.categoryToCategoryDTO(category);
    }

    @Transactional
    public void updateCategory(MultipartFile image,
                               MultipartFile thumbnail,
                               Integer categoryId,
                               String title,
                               String categoryParentTitle
    ) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if(checkCategoryExitByTitle(title) && !category.getTitle().equals(title)) {
            throw new ResourceNotFoundException(String.format("category with title : [%s] existed" , title));
        }

        if(categoryParentTitle != null) {
            Category parent = categoryRepository.
                    findByTitle(categoryParentTitle).
                    orElseThrow(() ->
                            new ResourceNotFoundException(
                                    String.format("category parent with id: %s not found", categoryParentTitle)
                            ));
            category.setCategoryParent(parent);
        } else {
            category.setCategoryParent(null);
        }

        category.setTitle(title);

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


    @Transactional
    public void addCategory(MultipartFile image, MultipartFile thumbnail, String title, String categoryParentTitle) {
        Category category = new Category();
        if(checkCategoryExitByTitle(title) && !category.getTitle().equals(title)) {
            throw new ResourceNotFoundException(String.format("category with title : [%s] existed" , title));
        }
        category.setTitle(title);


        if(categoryParentTitle != null) {
            Category parent = categoryRepository.
                    findByTitle(categoryParentTitle).
                    orElseThrow(() ->
                            new ResourceNotFoundException(
                                    String.format("category parent with title: %s not found", categoryParentTitle)
                            ));
            category.setCategoryParent(parent);
        }

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

    public CategoryResponseDTO getByIdForAdmin(Integer categoryId) {
        Category category = categoryRepository.findByIdWithParent(categoryId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("category with id : [%d] existed" , categoryId)));
        return categoryResponseMapper.category2CategoryResponseDTO(category);
    }

    private boolean checkCategoryExitByTitle(String title) {
        return categoryRepository.findByTitle(title).isPresent();
    }

    public List<CategoryResponseDTO> listAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryResponseMapper::category2CategoryResponseDTO).toList();
    }

    public void addPlaylist(Integer categoryId, Long playlistId) {
        Category category = categoryRepository.
                findById(categoryId).
                orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("category  with id: %d not found", categoryId)
                        ));
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("playlist  with id: %d not found", playlistId)
                        ));

        category.addPlaylist(playlist);
        categoryRepository.save(category);
    }

    public void removePlaylist(Integer categoryId, Long playlistId) {
        Category category = categoryRepository.
                findById(categoryId).
                orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("category  with id: %d not found", categoryId)
                        ));
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("playlist  with id: %d not found", playlistId)
                        ));

        category.removePlaylist(playlist);
        categoryRepository.save(category);
    }
}
