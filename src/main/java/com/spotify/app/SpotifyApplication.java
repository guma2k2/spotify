package com.spotify.app;

import com.spotify.app.enums.Gender;
import com.spotify.app.enums.Genre;
import com.spotify.app.model.*;
import com.spotify.app.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@RestController
public class SpotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpotifyApplication.class, args) ;
    }
}
