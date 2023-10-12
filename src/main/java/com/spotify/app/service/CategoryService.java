package com.spotify.app.service;

import com.spotify.app.dto.CategoryDTO;
import com.spotify.app.dto.response.CategoryResponse;
import com.spotify.app.exception.DuplicateResourceException;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.CategoryMapper;
import com.spotify.app.mapper.CategoryResponseMapper;
import com.spotify.app.model.Album;
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

    private final int identifyOfCategoryHome = 1;

    private final S3Service s3Service;

    public Set<CategoryDTO> listParent() {
        Set<Category> categories = categoryRepository.listAllParent();
        return CategoryMapper.INSTANCE.categoriesToCategoriesDTO(categories);
    }

    public Set<CategoryDTO> listByParentId(Integer parentId) {
        Set<Category> categories = categoryRepository.listAllChildByParenId(parentId);
        return CategoryMapper.INSTANCE.categoriesToCategoriesDTO(categories);
    }

    public Category get(Integer cateId) {
        return categoryRepository.
                findById(cateId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("category with id not found", cateId)));
    }

    public Set<CategoryDTO>  findCategoryNameHome() {
        return listByParentId(identifyOfCategoryHome);
    }

    public CategoryDTO findByIdCustom(Integer id) {
        Category category  = categoryRepository.
                findByIdCustom(id).
                orElseThrow(() -> new ResourceNotFoundException("category not found"));
        return categoryMapper.categoryToCategoryDTO(category);
    }


    private  void checkCategoryExitByTitleWhenUpdate(Category underCheck,String title) {
        boolean check = categoryRepository.findByTitle(title).isPresent();
        if (check && !underCheck.getTitle().equals(title)) {
            throw new DuplicateResourceException(String.format("category with title : [%s] existed" , title));
        }
    }

    private void checkCategoryExitByTitle(String title) {
        boolean check = categoryRepository.findByTitle(title).isPresent();
        if(check) {
            throw new DuplicateResourceException(String.format("category with title : [%s] existed" , title));
        }
    }

    @Transactional
    public void updateCategory(
                               Integer categoryId,
                               String title,
                               String categoryParentTitle
    ) {
        Category underUpdate = get(categoryId);

        checkCategoryExitByTitleWhenUpdate(underUpdate, title);
        underUpdate.setTitle(title);

        if(categoryParentTitle != null) {
            Category parent = getByTitle(categoryParentTitle);
            underUpdate.setCategoryParent(parent);
        }
        categoryRepository.save(underUpdate);
    }


    @Transactional
    public void addCategory(
                    String title,
                    String categoryParentTitle
    ) {
        Category underSave = new Category();
        checkCategoryExitByTitle(title);
        underSave.setTitle(title);

        if(categoryParentTitle != null) {
            Category parent = getByTitle(categoryParentTitle);
            underSave.setCategoryParent(parent);
        } else {
            underSave.setCategoryParent(null);
        }


        categoryRepository.save(underSave);
    }


    public Category getByTitle(String title) {
        return categoryRepository.
                findByTitle(title).
                orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("category with title: %s not found", title)
                        ));
    }

    public CategoryResponse getByIdForAdmin(Integer categoryId) {
        Category category = categoryRepository.
                findByIdWithParent(categoryId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("category with id : [%d] existed", categoryId)));
        return categoryResponseMapper.category2CategoryResponse(category);
    }

    public List<CategoryResponse> listAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryResponseMapper::category2CategoryResponse).toList();
    }

    public void addPlaylist(Integer categoryId, Long playlistId) {
        Category category = get(categoryId);

        Playlist playlist = getPlaylistByPlaylistId(playlistId);

        category.addPlaylist(playlist);
        categoryRepository.save(category);
    }

    public void removePlaylist(Integer categoryId, Long playlistId) {
        Category category = get(categoryId);

        Playlist playlist = getPlaylistByPlaylistId(playlistId);

        category.removePlaylist(playlist);
        categoryRepository.save(category);
    }

    public Playlist getPlaylistByPlaylistId(Long playlistId) {
        return playlistRepository
                .findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("playlist with id: [%d] not found",playlistId)));
    }

    public void saveCategoryImage(MultipartFile image, Integer categoryId) {
        Category underSave = get(categoryId);
        if (image != null) {
            underSave.setImage(image.getOriginalFilename());
            try {
                s3Service.putObject(
                        String.format("category/image/%d/%s",categoryId,image.getOriginalFilename()),image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            categoryRepository.save(underSave);
        }
    }

    public void saveCategoryThumbnail(MultipartFile thumbnail, Integer categoryId) {
        Category underSave = get(categoryId);
        if (thumbnail != null) {
            underSave.setImage(thumbnail.getOriginalFilename());
            try {
                s3Service.putObject(
                        String.format("category/thumbnail/%d/%s",categoryId,thumbnail.getOriginalFilename()),thumbnail.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            categoryRepository.save(underSave);
        }
    }

    public byte[] getCategoryImage(Integer categoryId) {
        Category underGet = get(categoryId);
        if (underGet.getImage().isEmpty()) {
            throw new ResourceNotFoundException(
                    "category id :[%d] not found image".formatted(categoryId));
        }

        byte[] albumImage = s3Service.getObject(
                "category/image/%d/%s".formatted(categoryId, underGet.getImage())
        );
        return albumImage;
    }

    public byte[] getCategoryThumbnail(Integer categoryId) {
        Category underGet = get(categoryId);
        if (underGet.getThumbnail().isEmpty()) {
            throw new ResourceNotFoundException(
                    "category id :[%d] not found thumbnail".formatted(categoryId));
        }

        byte[] categoryThumbnail = s3Service.getObject(
                "category/thumbnail/%d/%s".formatted(categoryId, underGet.getThumbnail())
        );
        return categoryThumbnail;
    }

}
