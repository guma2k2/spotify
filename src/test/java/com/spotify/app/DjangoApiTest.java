package com.spotify.app;

import static org.hamcrest.Matchers.*;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@SpringBootTest
public class DjangoApiTest {

    @Autowired
    private  RestTemplate restTemplate ;

    @Test
    public void canGetLabelWhenSendSearchText() {
        // given
        String url = "http://127.0.0.1:8000/sentiment";
        String text = "nhạc vui";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("text", text);
        // when
        ResponseEntity<String> underTest = restTemplate.getForEntity(builder.toUriString(),String.class);
        System.out.println(underTest.getBody());
        // then
        assert (underTest.getStatusCode().is2xxSuccessful() );
    }

}
