package com.seberino.chat.mapper;

import com.seberino.chat.entity.Attachment;
import com.seberino.chat.entity.Message;
import com.seberino.chat.entity.User;
import com.seberino.chat.request.MessageRequest;
import org.springframework.stereotype.Component;

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
}
