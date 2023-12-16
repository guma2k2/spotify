package com.spotify.app.category;


import com.spotify.app.controller.CategoryController;
import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.CategoryDTO;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.service.CategoryService;
import com.spotify.app.service.SearchService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = CategoryController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
})
public class CategoryControllerTest {
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private CategoryService categoryService;

        @MockBean
        private SearchService searchService;

        private CategoryDTO categoryParentDTO ;
        private CategoryDTO categoryChild1DTO ;
        private CategoryDTO categoryChild2DTO ;

        private  String prefixUrl = "/api/v1/category";


        @BeforeEach
        public void setUp () {
                categoryParentDTO = new CategoryDTO(1, "category parent", true, "image.png","thumb.png",null);
                categoryChild1DTO = new CategoryDTO(2, "category child1", true, "image.png","thumb.png",null);
                categoryChild2DTO = new CategoryDTO(3, "category child2", true, "image.png","thumb.png",null);
        }

        // find all child by parent
        @Test
        public void canFillAllChildByParent () throws Exception {
              // given
              Set<CategoryDTO> categoryDTOSet = new HashSet<>();
              categoryDTOSet.add(categoryChild1DTO);
              categoryDTOSet.add(categoryChild2DTO);
              Integer categoryParentId = 1 ;
              // when
              when(categoryService.listByParentId(categoryParentId)).thenReturn(categoryDTOSet);

              // then
              String url = String.format(prefixUrl+"/getChildBy/%d", categoryParentId);
              this.mockMvc.
                      perform(get(url))
                      .andExpect(status().isOk())
                      .andExpect(jsonPath("$", hasSize(2)))
                      .andExpect(jsonPath("$[0].title", Matchers.is(categoryChild1DTO.title())));
        }

        // get by id
        @Test
        public void canGetById () throws Exception {

             // given
             Integer categoryId = 1;
             String url = String.format(prefixUrl.concat("/%d"), categoryId);

             // when
             when(categoryService.findByIdCustom(categoryId)).thenReturn(categoryParentDTO);

             // then
             this.mockMvc
                     .perform(get(url))
                     .andExpect(status().isOk())
                     .andExpect(jsonPath("$.title", Matchers.is(categoryParentDTO.title())));


        }
        // get by id fail
        @Test
        public void cannotGetById () throws Exception {
                // given
                Integer categoryId = 0;
                String url = String.format(prefixUrl.concat("/%d"), categoryId);

                // when
                when(categoryService.findByIdCustom(categoryId)).thenThrow(new ResourceNotFoundException("category not found"));

                // then
                String expectMessage = "category not found";
                this.mockMvc
                        .perform(get(url))
                        .andExpect(status().isNotFound())
                        .andDo(print())
                        .andExpect(jsonPath("$.errors[0]", Matchers.is(expectMessage)));


        }
}
