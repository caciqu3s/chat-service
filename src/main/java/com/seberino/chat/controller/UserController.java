package com.seberino.chat.controller;

import com.seberino.chat.dto.UserDto;
import com.seberino.chat.entity.User;
import com.seberino.chat.model.LoginRequest;
import com.seberino.chat.model.TokenResponse;
import com.seberino.chat.model.UserRequest;
import com.seberino.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<TokenResponse> createUser(@RequestBody UserRequest request) {
        service.createUser(request);
        return authUser(LoginRequest.builder().email(request.getEmail()).password(request.getPassword()).build());
    }

    @GetMapping
    public ResponseEntity<User> getUserInfo(Principal principal) {
        return ResponseEntity.ok(service.getUserByEmail(principal.getName()));
    }
    
    @GetMapping("/contacts")
    public ResponseEntity<List<UserDto>> getContacts(Principal principal) {
        return ResponseEntity.ok(service.getContacts(service.getUserByEmail(principal.getName())));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authUser(@RequestBody LoginRequest request) {
        this.authenticate(request);
        return ResponseEntity.ok(TokenResponse.builder().token(this.service.getToken(request)).build());
    }

    private void authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }
}
