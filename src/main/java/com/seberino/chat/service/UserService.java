package com.seberino.chat.service;

import com.seberino.chat.JwtUtil;
import com.seberino.chat.dto.UserDto;
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
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final JwtUtil jwtUtil;
    private final AttachmentService attachmentService;
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

    public List<UserDto> getContacts(User user) {
        return UserMapper.getContacts(user).stream().map(contact -> UserMapper.toDto(contact)).collect(Collectors.toList());
    }

    @Transactional
    public UserDto setPicture(User user, MultipartFile picture) throws IOException {
        user.setPicture(attachmentService.createAttachment(picture));

        return UserMapper.toDto(user);
    }

    public String getToken(LoginRequest request) {
        return this.jwtUtil.generateToken(repository.findByEmail(request.getEmail()).orElseThrow());
    }
}
