package com.bombombom.devs.mysql.user;

import com.bombombom.devs.domain.user.model.User;
import com.bombombom.devs.mysql.user.entity.UserEntity;

public class Mapper {

    public static User toModel(UserEntity userEntity) {

        return User.builder().build();
    }

    public static UserEntity toEntity(User user) {

        return UserEntity.builder().build();
    }
}
