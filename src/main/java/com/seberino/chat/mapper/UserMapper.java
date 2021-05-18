package com.seberino.chat.mapper;

import com.seberino.chat.dto.UserDto;
import com.seberino.chat.entity.User;
import com.seberino.chat.model.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public static User toUser(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .build();
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .picture(user.getPicture())
                .build();
    }

    public static List<User> getContacts(User user) {
        List<User> senderUsers = user.getReceivedMessages().stream().map(messages -> messages.getSenderUser()).collect(Collectors.toList());
        List<User> recipientUsers = user.getSentMessages().stream().map(messages -> messages.getRecipientUser()).collect(Collectors.toList());

        List<User> contacts = new ArrayList<>();
        contacts.addAll(senderUsers);
        contacts.addAll(recipientUsers);

        return contacts;
    }
}
