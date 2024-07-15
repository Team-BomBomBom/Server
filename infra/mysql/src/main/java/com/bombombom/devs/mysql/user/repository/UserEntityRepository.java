package com.bombombom.devs.mysql.user.repository;

import com.bombombom.devs.domain.user.model.User;
import com.bombombom.devs.domain.user.repository.UserRepository;
import com.bombombom.devs.mysql.user.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserEntityRepository implements UserRepository {

    UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserEntity::toModel);
    }

    @Override
    public List<User> findAllById(Iterable<Long> ids) {
        return userJpaRepository.findAllById(ids).stream().map(UserEntity::toModel).toList();

    }

    @Override
    public Optional<User> findUserByUsername(String username) {

        return userJpaRepository.findUserByUsername(username).map(UserEntity::toModel);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.fromModel(user)).toModel();
    }

    @Override
    public void deleteByUsername(String username) {
        userJpaRepository.deleteByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }
}
