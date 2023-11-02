package com.spotify.app.song;

import com.spotify.app.AbstractTestcontainers;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class SongIt extends AbstractTestcontainers {
    private static final String SONG_PATH = "/api/v1/song";

    @Test
    public void canSaveSong() {

    }

    @Test
    public void canUpdateSong() {

    }

    @Test
    public void canUpdateStatusSong() {

    }

    @Test
    public void canAddCollapse() {

    }
    @Test
    public void canRemoveCollapse() {

    }
}
