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
import com.spotify.app.model.Song;
import com.spotify.app.repository.CategoryRepository;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.utility.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    private final CloudinaryService cloudinaryService;

    public Set<CategoryDTO> listParent() {
        Set<Category> categories = categoryRepository.listAllParent();
        return categoryMapper.categoriesToCategoriesDTO(categories);
    }

    public Set<CategoryDTO> listByParentId(Integer parentId) {
        Set<Category> categories = categoryRepository.listAllChildByParenId(parentId);
        return categoryMapper.categoriesToCategoriesDTO(categories);
    }

    public Category get(Integer cateId) {
        return categoryRepository.
                findById(cateId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("category with id %d not found", cateId)));
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

    @Transactional
    public void updateCategory( Integer categoryId, String title, String categoryParentTitle
    ) {
        Category underUpdate = categoryRepository.
                findById(categoryId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("category with id %d not found", categoryId)));

        boolean check = categoryRepository.findByTitle(title).isPresent();
        if (check && !underUpdate.getTitle().equals(title)) {
            throw new DuplicateResourceException(String.format("category with title : [%s] existed" , title));
        }
        underUpdate.setTitle(title);

        if(categoryParentTitle != null) {
            Category parent = categoryRepository.
                    findByTitle(title).
                    orElseThrow(() ->
                            new ResourceNotFoundException(
                                    String.format("category with title: %s not found", title)
                            ));
            underUpdate.setCategoryParent(parent);
        }
        categoryRepository.save(underUpdate);
    }


    @Transactional
    public void addCategory(String title, String categoryParentTitle) {
        Category underSave = new Category();
        boolean check = categoryRepository.findByTitle(title).isPresent();
        if(check) {
            throw new DuplicateResourceException(String.format("category with title : [%s] existed" , title));
        }
        underSave.setTitle(title);

        if(categoryParentTitle != null) {
            Category parent = categoryRepository.
                    findByTitle(title).
                    orElseThrow(() ->
                            new ResourceNotFoundException(
                                    String.format("category with title: %s not found", title)
                            ));
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
                        new ResourceNotFoundException(String.format("playlist with id: [%d] not found", playlistId)));
    }

    public void saveCategoryImage(MultipartFile image, Integer categoryId) {
        Category underSave = get(categoryId);
        if (!image.isEmpty()) {
            String fileDir = String.format("category-images/%d/", categoryId);

            if(underSave.getImage() != null) {
                String currentFileDir = underSave.getImage();
                cloudinaryService.destroyFile(currentFileDir);
            }
            String newFileDir = fileDir + StringUtils.cleanPath(image.getOriginalFilename());
            String newPath = cloudinaryService.uploadFile(image, newFileDir);
            underSave.setImage(newPath);
            categoryRepository.save(underSave);
        }
    }

    public void saveCategoryThumbnail(MultipartFile thumbnail, Integer categoryId) {
        Category underSave = get(categoryId);
        if (!thumbnail.isEmpty()) {
            String fileDir = String.format("category-thumbnails/%d/", categoryId);

            if(underSave.getThumbnail() != null) {
                String currentFileDir = underSave.getThumbnail();
                cloudinaryService.destroyFile(currentFileDir);
            }
            String newFileDir = fileDir + StringUtils.cleanPath(thumbnail.getOriginalFilename());
            String newPath = cloudinaryService.uploadFile(thumbnail, newFileDir);
            underSave.setThumbnail(newPath);
            categoryRepository.save(underSave);
        }
    }

    public String updateStatusCategory(Integer categoryId) {
        Category category = get(categoryId);
        category.setStatus(!category.isStatus());
        categoryRepository.saveAndFlush(category);
        String status = !category.isStatus() ? "disabled" : "enabled";
        return String.format("category with id: %d is ".concat(status),categoryId);
    }

}
