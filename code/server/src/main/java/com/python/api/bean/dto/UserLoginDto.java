package com.python.api.bean.dto;

import com.python.api.entity.User;
import lombok.Data;

@Data
public class UserLoginDto {
    private User user;

    private String token;

    public UserLoginDto(User t, String token) {
        this.user = t;
        this.token = token;
    }
}
