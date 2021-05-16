package com.seberino.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_attachment_id", referencedColumnName = "id", nullable = true)
    private Attachment image;

    @OneToMany(mappedBy="senderUser")
    private List<Message> sentMessages;

    @OneToMany(mappedBy="recipientUser")
    private List<Message> receivedMessages;
}
