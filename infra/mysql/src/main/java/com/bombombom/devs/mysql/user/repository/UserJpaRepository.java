package com.bombombom.devs.mysql.user.repository;

import com.bombombom.devs.mysql.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);
}
