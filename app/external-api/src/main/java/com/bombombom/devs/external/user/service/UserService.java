package com.bombombom.devs.external.user.service;

import com.bombombom.devs.domain.user.model.User;
import com.bombombom.devs.domain.user.repository.UserRepository;
import com.bombombom.devs.external.user.service.dto.SignupCommand;
import com.bombombom.devs.external.user.service.dto.UserProfileResult;
import com.bombombom.devs.user.exception.ExistUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addUser(SignupCommand signupCommand) {
        if (userRepository.existsByUsername(signupCommand.username())) {
            throw new ExistUsernameException();
        }
        User user = User.signup(signupCommand.username(),
            passwordEncoder.encode(signupCommand.password()), signupCommand.introduce());

        userRepository.save(user);
    }

    public UserProfileResult findById(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException("User Not Found"));
        return UserProfileResult.fromModel(user);
    }
}
