package com.spotify.app.security.auth;
import com.spotify.app.exception.HeaderNotFoundException;
import com.spotify.app.exception.UserException;
import com.spotify.app.mapper.UserMapper;
import com.spotify.app.model.Role;
import com.spotify.app.model.User;
import com.spotify.app.repository.RoleRepository;
import com.spotify.app.repository.UserRepository;
import com.spotify.app.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;



    public AuthenticationResponse register(RegisterRequest request) {
//        log.info(request.getEmail());
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw  new UserException("The email exited");
        }

        Role role = roleRepository.findByName("ROLE_CUSTOMER").orElseThrow();

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        User savedUser = userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(UserMapper.INSTANCE.userToUserDTO(savedUser))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException("User cannot be found by email"));

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(UserMapper.INSTANCE.userToUserDTO(user))
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

//        log.info(refreshToken);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() ->
                            new UserException(String.format("The user with email: %s was not found", userEmail)));
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .user(UserMapper.INSTANCE.userToUserDTO(user))
                        .build();
            }
        }
        return  authResponse;
    }

}
