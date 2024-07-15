package com.bombombom.devs.mysql.user.entity;

import com.bombombom.devs.domain.user.enums.Role;
import com.bombombom.devs.mysql.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    private Long id;

    private String username;

    private String password;

    private Role role;

    private String image;
    private String introduce;
    private String baekjoon;
    private Integer reliability;
    private Integer money;

    public static UserEntity signup(String username, String password, String introduce) {
        return UserEntity.builder()
            .username(username)
            .password(password)
            .introduce(introduce)
            .role(Role.USER)
            .reliability(0)
            .money(0)
            .build();
    }

    public void payMoney(Integer money) {
        if (money < 0) {
            throw new IllegalStateException("Money must be positive");
        }
        if (this.money < money) {
            throw new IllegalStateException("User have Not enough money");
        }
        this.money -= money;
    }
    
}
