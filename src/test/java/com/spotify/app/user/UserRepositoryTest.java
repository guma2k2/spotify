package com.spotify.app.user;

import com.spotify.app.AbstractTestcontainers;
import com.spotify.app.TestConfig;
import com.spotify.app.enums.Gender;
import com.spotify.app.enums.Genre;
import com.spotify.app.model.Role;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import com.spotify.app.repository.RoleRepository;
import com.spotify.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class UserRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private UserRepository underTest ;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        underTest.deleteAll();
    }


    @Test
    @DisplayName("Test create user")
    public void canCreateUser() {
        // given
        String email = "thuan@gmail.com";
        User user = User
                .builder()
                .firstName("firstName")
                .lastName("lastName")
                .email("thuan@gmail.com")
                .dateOfBrith(LocalDateTime.of(2002,7,30,0,0))
                .build();
        underTest.save(user);

        // when
        var actual = underTest.findByEmail(email);

        // then
        assertThat(actual.isPresent());
    }



    @Test
    @DisplayName("Test find user by id")
    public void existUserById() {
        // given
        String email = "thuan@gmail.com";
        User user = User
                .builder()
                .firstName("firstName")
                .lastName("lastName")
                .email(email)
                .dateOfBrith(LocalDateTime.of(2002,7,30,0,0))
                .build();
        underTest.save(user);

        long id = underTest.
                findAll().
                stream().
                filter(u-> u.getEmail().equals(email)).
                map(User::getId).
                findFirst().
                orElseThrow();

        // when
        var actual = underTest.findById(id);

        // then
        assertThat(actual.isPresent());
    }


    @Test
    @DisplayName("Test find user by keyword")
    public void existUser__whenFindByKeyword() {
        // given
        String keyword = "a";
        String email = "thuan@gmail.com";
        User user = User
                .builder()
                .firstName("abc")
                .lastName("def")
                .email(email)
                .dateOfBrith(LocalDateTime.of(2002,7,30,0,0))
                .build();
        underTest.save(user);


        // when
        var actual = underTest.findAllWithKeyword(keyword,any());

        // then
        assertThat(actual.getSize() > 0);
    }

    @Test
    @DisplayName("Test find user by id")
    public void whenFindUserById_thenReturnSongAndRoleNotNull() {
        // given
        Role role = roleRepository.save(Role.builder().name("role_user").build());
        String songName = "name_song";
        Song song = Song.builder().name(songName).genre(Genre.CLASSICAL).build();
        String email = "thuan@gmail.com";
        User user = User
                .builder()
                .firstName("firstName")
                .lastName("lastName")
                .email(email)
                .dateOfBrith(LocalDateTime.of(2002,7,30,0,0))
                .role(role)
                .build();
        user.addSong(song);
        
        underTest.save(user);

        // when
        var actual = underTest.findByEmail(email);

        // then
        assertAll(
                () -> assertThat(actual.isPresent()),
                () -> assertEquals(role.getName(), actual.get().getRole().getName(),true),
                () -> assertThat(actual.get().getSongs().size() > 0)
        );
    }

    @Test
    public void exitUserByIdFailWhenIdNotExit() {
        // given
        long userid = 0l;

        //when
        var actual = underTest.findById(userid);

        // then
        assertThat(actual).isNotPresent();
    }





}
