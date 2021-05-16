package com.seberino.chat.service;

import com.seberino.chat.entity.User;
import com.seberino.chat.mapper.UserMapper;
import com.seberino.chat.repository.UserRepository;
import com.seberino.chat.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User createUser(UserRequest request) {
        return repository.save(UserMapper.toUser(request));
    }
}
