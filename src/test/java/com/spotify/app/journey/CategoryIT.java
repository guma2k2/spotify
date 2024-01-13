package com.spotify.app.journey;

import com.spotify.app.AbstractTestcontainers;
import com.spotify.app.dto.CategoryDTO;
import com.spotify.app.dto.response.UserNoAssociationResponse;
import com.spotify.app.security.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpResponse;
import java.util.Set;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class CategoryIT extends AbstractTestcontainers {

    private static final String CATEGORY_PATH = "/api/v1/category";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void canGetAllCategoryParent() {

        // given
        String url = CATEGORY_PATH.concat("/getAllParent");
        // when
        ResponseEntity<Set> response = restTemplate.getForEntity(url, Set.class);
        // then
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
