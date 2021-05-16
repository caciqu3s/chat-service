package com.seberino.chat.controller;

import com.seberino.chat.entity.Message;
import com.seberino.chat.request.MessageRequest;
import com.seberino.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestPart("request")MessageRequest request, @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(messageService.sendMessage(request, file));
    }
}
