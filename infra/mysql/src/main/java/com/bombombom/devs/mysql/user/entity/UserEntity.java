package com.bombombom.devs.mysql.user.entity;

import com.bombombom.devs.domain.user.enums.Role;
import com.bombombom.devs.domain.user.model.User;
import com.bombombom.devs.mysql.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
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

    public User toModel() {
        return User.builder()
            .id(getId())
            .username(getUsername())
            .password(getPassword())
            .money(getMoney())
            .role(getRole())
            .baekjoon(getBaekjoon())
            .introduce(getIntroduce())
            .image(getImage())
            .reliability(getReliability())
            .build();
    }

    public static UserEntity fromModel(User user) {
        return UserEntity.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .money(user.getMoney())
            .role(user.getRole())
            .baekjoon(user.getBaekjoon())
            .introduce(user.getIntroduce())
            .image(user.getImage())
            .reliability(user.getReliability())
            .build();
    }
}
