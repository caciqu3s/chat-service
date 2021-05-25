package com.seberino.chat.service;

import com.seberino.chat.dto.UserDto;
import com.seberino.chat.entity.Attachment;
import com.seberino.chat.entity.Message;
import com.seberino.chat.entity.User;
import com.seberino.chat.mapper.MessageMapper;
import com.seberino.chat.repository.MessageRepository;
import com.seberino.chat.repository.UserRepository;
import com.seberino.chat.model.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final AttachmentService attachmentService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @Transactional
    public Message sendMessage(MessageRequest request, MultipartFile attachmentFile) throws IOException {
        User recipient = userRepository.findByEmail(request.getRecipient()).orElseThrow();
        User sender = userRepository.findByEmail(request.getSender()).orElseThrow();
        Attachment attachment = attachmentService.createAttachment(attachmentFile);

        Message message = this.messageRepository.save(MessageMapper.toMessage(request, sender, recipient, attachment));
        this.sendToSocket(message);
        return message;
    }

    public List<Message> getMessagesFromUserEmail(String email) {
        return messageRepository.findBySenderUserEmailOrRecipientUserEmail(email, email).orElseThrow();
    }

    private void sendToSocket(Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getRecipientUser().getEmail(), "/queue/reply", message);
    }
}
