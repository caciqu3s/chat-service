package com.seberino.chat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_attachment_id", referencedColumnName = "id", nullable = true)
    private Attachment picture;

    @OneToMany(mappedBy="senderUser")
    @JsonBackReference
    private List<Message> sentMessages;

    @OneToMany(mappedBy="recipientUser")
    @JsonBackReference
    private List<Message> receivedMessages;
}
