package com.seberino.chat.controller;

import com.seberino.chat.entity.Message;
import com.seberino.chat.model.MessageRequest;
import com.seberino.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestPart("request")MessageRequest request, @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(messageService.sendMessage(request, file));
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getMessagesFromConversation(Principal principal, @RequestParam String email) {
        return ResponseEntity.ok(messageService.getMessagesFromUserEmail(email));
    }
}
