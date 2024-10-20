package com.bombombom.devs.external.global.security;

import com.bombombom.devs.security.AppUserDetails;
import com.bombombom.devs.user.model.User;
import com.bombombom.devs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
        return AppUserDetails.fromUser(user);
    }

}
