//package com.spotify.app.category;
//
//import com.spotify.app.AbstractTestcontainers;
//import com.spotify.app.TestConfig;
//import com.spotify.app.model.Category;
//import com.spotify.app.repository.CategoryRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//import java.util.List;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import({TestConfig.class})
//public class CategoryRepositoryTest extends AbstractTestcontainers {
//
//
//    @Autowired
//    private CategoryRepository underTest ;
//
//
//    @Test
//    public void canListAllChildCategoryByParentCategory() {
//        // given
//        Integer categoryParentId = 1;
//        // when
//        Set<Category> categoryChild = underTest.listAllChildByParenId(categoryParentId);
//        // then
//        assertThat(categoryChild.size()).isGreaterThan(0);
//    }
//
//}
