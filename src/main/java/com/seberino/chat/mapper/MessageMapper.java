package com.seberino.chat.mapper;

import com.seberino.chat.dto.GetMessagesResponse;
import com.seberino.chat.dto.MessageDto;
import com.seberino.chat.entity.Attachment;
import com.seberino.chat.entity.Message;
import com.seberino.chat.entity.User;
import com.seberino.chat.model.MessageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageMapper {
    public static Message toMessage(MessageRequest request, User sender, User recipient, Attachment attachment) {
        return Message.builder()
                .attachment(attachment)
                .recipientUser(recipient)
                .senderUser(sender)
                .text(request.getText())
                .build();
    }

    public static MessageDto toDto(Message message) {
        return MessageDto.builder()
                .attachment(message.getAttachment())
                .sender(UserMapper.toDto(message.getSenderUser()))
                .text(message.getText())
                .build();
    }
}
