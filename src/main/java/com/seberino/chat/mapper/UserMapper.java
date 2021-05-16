package com.seberino.chat.mapper;

import com.seberino.chat.entity.User;
import com.seberino.chat.request.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static User toUser(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }
}
