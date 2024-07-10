package com.bombombom.devs.domain.user.repository;

import com.bombombom.devs.domain.user.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    List<User> findAllById(Iterable<Long> ids);

    Optional<User> findUserByUsername(String username);

    User save(User user);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);
}
