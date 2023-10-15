package com.spotify.app.security.auth;
import com.spotify.app.enums.Gender;
import com.spotify.app.exception.HeaderNotFoundException;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.UserMapper;
import com.spotify.app.mapper.UserResponseMapper;
import com.spotify.app.model.Playlist;
import com.spotify.app.model.Role;
import com.spotify.app.model.User;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.repository.RoleRepository;
import com.spotify.app.repository.UserRepository;
import com.spotify.app.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final UserResponseMapper userResponseMapper;

    public AuthenticationResponse register(RegisterRequest request) {
//        log.info(request.getEmail());
        if(userRepository.findByEmail(request.email()).isPresent()){
            throw  new ResourceNotFoundException(String.format("email: %s exited",request.email()));
        }

        Role role = roleRepository.findByName("ROLE_CUSTOMER").orElseThrow();

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .gender(Gender.valueOf(request.gender()))
                .createdOn(LocalDateTime.now())
                .dateOfBrith(LocalDateTime.of(request.year(),request.month(),request.day(),0,0))
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .build();
        User savedUser = userRepository.save(user);

        // Trigger create playlist liked song when register success
        Playlist playlist = Playlist.builder()
                .name("Liked Songs")
                .build();
        playlist.addUser(savedUser);
        playlistRepository.save(playlist);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userResponseMapper.userToUserResponse(user))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User cannot be found by email"));

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userResponseMapper.userToUserResponse(user))
                .build();
    }


    public AuthenticationResponse refreshToken(
            HttpServletRequest request
    )  {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            throw new HeaderNotFoundException("Header was not valid");
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUserName(refreshToken);
        AuthenticationResponse authResponse = null;

        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(String.format("The user with email: %s was not found", userEmail)));
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .user(userResponseMapper.userToUserResponse(user))
                        .build();
            }
        }
        return  authResponse;
    }

}
