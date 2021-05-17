package com.seberino.chat.repository;

import com.seberino.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    Optional<List<Message>> findBySenderUserEmailOrRecipientUserEmail(String senderEmail, String recipientEmail);
}
