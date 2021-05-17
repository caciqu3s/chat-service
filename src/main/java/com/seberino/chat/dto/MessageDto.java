package com.seberino.chat.dto;

import com.seberino.chat.entity.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String text;

    private Attachment attachment;

    private UserDto sender;
}
