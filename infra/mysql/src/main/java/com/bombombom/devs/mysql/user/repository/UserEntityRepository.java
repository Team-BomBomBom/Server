package com.bombombom.devs.mysql.user.repository;

import com.bombombom.devs.domain.user.model.User;
import com.bombombom.devs.domain.user.repository.UserRepository;
import com.bombombom.devs.mysql.user.Mapper;
import java.util.List;
import java.util.Optional;

public class UserEntityRepository implements UserRepository {

    UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(Mapper::toModel);
    }

    @Override
    public List<User> findAllById(Iterable<Long> ids) {
        return userJpaRepository.findAllById(ids).stream().map(Mapper::toModel).toList();
        
    }

    @Override
    public Optional<User> findUserByUsername(String username) {

        return userJpaRepository.findUserByUsername(username).map(Mapper::toModel);
    }

    @Override
    public User save(User user) {
        return Mapper.toModel(userJpaRepository.save(Mapper.toEntity(user)));
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
