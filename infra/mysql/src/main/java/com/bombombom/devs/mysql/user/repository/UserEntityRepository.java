package com.bombombom.devs.mysql.user.repository;

import com.bombombom.devs.domain.user.model.User;
import com.bombombom.devs.domain.user.repository.UserRepository;
import java.util.Optional;

public class UserEntityRepository implements UserRepository {

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void deleteByUsername(String username) {

    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }
}
