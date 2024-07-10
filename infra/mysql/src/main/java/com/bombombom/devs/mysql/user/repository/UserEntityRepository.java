package com.bombombom.devs.mysql.user.repository;

import com.bombombom.devs.domain.user.model.User;
import com.bombombom.devs.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;

public class UserEntityRepository implements UserRepository {

    UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAllById(Iterable<Long> ids) {
        return null;
    }

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
