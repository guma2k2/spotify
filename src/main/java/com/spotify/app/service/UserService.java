package com.spotify.app.service;

import com.spotify.app.dto.UserDTO;
import com.spotify.app.exception.UserException;
import com.spotify.app.mapper.UserMapper;
import com.spotify.app.model.User;
import com.spotify.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository ;

    public UserDTO getUserById(Long userId) {
        Optional<User> user = userRepository.findByIdCustom(userId) ;
        if(!user.isPresent()) {
            throw new UserException("User not found") ;
        }
        return UserMapper.INSTANCE.userToUserDTO(user.get()) ;
    }

    public void uploadPhoto(MultipartFile photo, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found")) ;
        try {
            user.setPhoto(photo.getBytes());
        } catch (IOException e) {
            throw new UserException(e.getMessage());
        }

        userRepository.save(user);
    }

    public User get(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
    }
}
