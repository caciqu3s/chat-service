package com.seberino.chat.service;

import com.seberino.chat.entity.Attachment;
import com.seberino.chat.entity.Message;
import com.seberino.chat.entity.User;
import com.seberino.chat.mapper.MessageMapper;
import com.seberino.chat.repository.MessageRepository;
import com.seberino.chat.repository.UserRepository;
import com.seberino.chat.request.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final AttachmentService attachmentService;

    @Transactional
    public Message sendMessage(MessageRequest request, MultipartFile attachmentFile) throws IOException {
        User recipient = userRepository.findByEmail(request.getRecipient()).orElseThrow();
        User sender = userRepository.findByEmail(request.getSender()).orElseThrow();
        Attachment attachment = attachmentService.createAttachment(attachmentFile);

        Message message = this.messageRepository.save(MessageMapper.toMessage(request, sender, recipient, attachment));

        // Implementar envio da mensagem no socket

        return message;
    }
}
