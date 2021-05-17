package com.seberino.chat.service;

import com.seberino.chat.JwtUtil;
import com.seberino.chat.entity.User;
import com.seberino.chat.mapper.UserMapper;
import com.seberino.chat.repository.UserRepository;
import com.seberino.chat.model.LoginRequest;
import com.seberino.chat.model.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final JwtUtil jwtUtil;
    //private final UserMapper userMapper;

    public User createUser(UserRequest request) {
        return repository.save(UserMapper.toUser(request));
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public String getToken(LoginRequest request) {
        return this.jwtUtil.generateToken(repository.findByEmail(request.getEmail()).orElseThrow());
    }
}
