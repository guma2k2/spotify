package com.spotify.app.song;

import com.spotify.app.AbstractTestcontainers;
import com.spotify.app.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class SongRepositoryTest extends AbstractTestcontainers {

    @Test
    public void testGetJdbcUrl() {
        String jdbcUrl = mySQLContainer.getJdbcUrl();
        System.out.println(mySQLContainer.getUsername());
        System.out.println(mySQLContainer.getPassword());
        assert (jdbcUrl.length() > 0);
    }
}
