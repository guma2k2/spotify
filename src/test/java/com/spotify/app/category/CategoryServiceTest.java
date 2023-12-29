package com.spotify.app.category;

import com.spotify.app.dto.CategoryDTO;
import com.spotify.app.exception.DuplicateResourceException;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.CategoryMapper;
import com.spotify.app.mapper.CategoryResponseMapper;
import com.spotify.app.model.Category;
import com.spotify.app.repository.CategoryRepository;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.service.CategoryService;
import com.spotify.app.service.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.module.ResolutionException;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {


    @Mock
    private  CategoryRepository categoryRepository ;

    @Mock
    private  CategoryResponseMapper categoryResponseMapper;
    @Mock
    private  CategoryMapper categoryMapper;
    @Mock
    private  PlaylistRepository playlistRepository;
    @Mock
    private  CloudinaryService cloudinaryService;
    private CategoryService underTest;

    @BeforeEach
    public void setUp () {
        underTest = new CategoryService(categoryRepository, categoryResponseMapper, categoryMapper, playlistRepository, cloudinaryService );
    }


    @Test
    public void canGetById () {
        // given
        Integer categoryId = 1;
        Category expected = new Category(categoryId);

        // when
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(expected));
        var actual = underTest.get(categoryId);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void cannotGetByIdWitNotExistedId () {
        // given
        Integer categoryId = 1;

        // when
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());


        // then
        assertThrows(ResourceNotFoundException.class, () -> {
            underTest.get(categoryId);
        });

        Mockito.verify(categoryRepository, Mockito.times(1)).findById(categoryId);
    }

    @Test
    public void canGetAllChildrenByParentCategory () {
        // given
        Integer categoryParentId = 1;
        Category category = new Category(2);
        Set<Category> categories = Set.of(category);
        CategoryDTO categoryDTO = new CategoryDTO(2);
        Set<CategoryDTO> expect = Set.of(categoryDTO);

        // when
        Mockito.when(categoryRepository.listAllChildByParenId(categoryParentId)).thenReturn(categories);
        Mockito.when(categoryMapper.categoriesToCategoriesDTO(Mockito.any())).thenReturn(expect);

        // then
        var actual = underTest.listByParentId(categoryParentId);

        assertThat(actual).isEqualTo(expect);
        Mockito.verify(categoryRepository, Mockito.times(1)).listAllChildByParenId(categoryParentId);
        Mockito.verify(categoryMapper, Mockito.times(1)).categoriesToCategoriesDTO(categories);
    }

    @Test
    public void cannotAddCategoryWithTileExist () {
        String title = "Test Category";
        String parentTitle = "Parent Category";

        // Mocking the CategoryRepository response
        Mockito.when(categoryRepository.findByTitle(title)).thenReturn(Optional.of(new Category()));

        // Calling the service method and expecting a DuplicateResourceException
        assertThrows(DuplicateResourceException.class, () -> {
            underTest.addCategory(title, parentTitle);
        });

        // Verifying that the repository method was called
        Mockito.verify(categoryRepository, Mockito.times(1)).findByTitle(title);
        Mockito.verify(categoryRepository, Mockito.never()).save(Mockito.any(Category.class));
    }

    @Test
    public void canAddCategory() {
        String title = "Test Category";

        // Mocking the CategoryRepository response
        Mockito.when(categoryRepository.findByTitle(title)).thenReturn(Optional.empty());
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Calling the service method
        underTest.addCategory(title, null);

        // Verifying that the repository methods were called
        Mockito.verify(categoryRepository, Mockito.times(1)).findByTitle(title);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));
    }
    @Test
    public void cannotUpdateCategoryWithExistedCategoryTitle () {
        Integer categoryId = 1;
        String existingTitle = "Existing Category";
        String newTitle = "New Category";
        String categoryParentTitle = "Parent Category";

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setTitle(existingTitle);

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        Mockito.when(categoryRepository.findByTitle(newTitle)).thenReturn(Optional.of(new Category()));

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () ->
                underTest.updateCategory(categoryId, newTitle, categoryParentTitle));

        // Verify that save method is not called
        Mockito.verify(categoryRepository, Mockito.never()).save(Mockito.any(Category.class));
    }
    @Test
    public void testUpdateStatusCategory() {
        // Sample data
        Integer categoryId = 1;

        // Mocking the CategoryRepository response
        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setStatus(true);
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        Mockito.when(categoryRepository.saveAndFlush(existingCategory)).thenAnswer(invocation -> invocation.getArgument(0));

        // Calling the service method
        String result = underTest.updateStatusCategory(categoryId);

        // Verifying that the repository methods were called
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(categoryId);
        Mockito.verify(categoryRepository, Mockito.times(1)).saveAndFlush(existingCategory);

        // Verifying the result
        String expectedStatusMessage = "category with id: 1 is disabled"; // Assuming initial status is true
        assertEquals(expectedStatusMessage, result);
    }

}
