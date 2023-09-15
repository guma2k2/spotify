package com.spotify.app.user;

import com.spotify.app.TestConfig;
import com.spotify.app.enums.Gender;
import com.spotify.app.enums.Genre;
import com.spotify.app.model.Role;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import com.spotify.app.repository.RoleRepository;
import com.spotify.app.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest ;


    @Test
    @DisplayName("Test fetching user by Id")
    public void whenGetUserById_thenReturnSuccess() {
        Long userId = 2L ;
        assert (underTest.findAll().size() > 0 );
        User user = underTest.findByIdCustom(userId).get() ;
        assert (user!=null);
        assert (user.getSongs().size() > 0);

    }


}
