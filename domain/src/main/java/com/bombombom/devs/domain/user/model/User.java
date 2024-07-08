package com.bombombom.devs.domain.user.model;

import com.bombombom.devs.domain.BaseModel;
import com.bombombom.devs.domain.user.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseModel {

    private Long id;

    private String username;

    private String password;

    private Role role;

    private String image;
    private String introduce;
    private String baekjoon;
    private Integer reliability;
    private Integer money;

//    public static User signup(String username, String password, String introduce) {
//        return User.builder()
//            .username(username)
//            .password(password)
//            .introduce(introduce)
//            .role(Role.USER)
//            .reliability(0)
//            .money(0)
//            .build();
//    }

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
