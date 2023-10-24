package com.spotify.app.album;


import com.spotify.app.SpotifyApplication;
import com.spotify.app.TestConfig;
import com.spotify.app.enums.Genre;
import com.spotify.app.model.Album;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import com.spotify.app.repository.AlbumRepository;
import com.spotify.app.repository.SongRepository;
import com.spotify.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class AlbumRepositoryTest {

}
