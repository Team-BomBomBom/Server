package com.bombombom.devs.domain.user.repository;

import com.bombombom.devs.domain.user.model.User;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByUsername(String username);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);
}
