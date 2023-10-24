package com.spotify.app.playlist;


import com.spotify.app.TestConfig;
import com.spotify.app.enums.Genre;
import com.spotify.app.model.Album;
import com.spotify.app.model.Category;
import com.spotify.app.model.Playlist;
import com.spotify.app.model.Song;
import com.spotify.app.repository.AlbumRepository;
import com.spotify.app.repository.CategoryRepository;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.repository.SongRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class PlaylistRepositoryTest {


}
