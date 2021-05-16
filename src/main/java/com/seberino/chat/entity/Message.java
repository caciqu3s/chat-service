package com.seberino.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_user", referencedColumnName = "id", nullable = false)
    private User senderUser;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_user", referencedColumnName = "id", nullable = false)
    private User recipientUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id", referencedColumnName = "id", nullable = true)
    private Attachment attachment;
}
