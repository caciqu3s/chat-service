package com.seberino.chat.mapper;

import com.google.cloud.storage.Blob;
import com.seberino.chat.entity.Attachment;

public class AttachmentMapper {
    public static Attachment toAttachment(Blob file) {
        return Attachment.builder()
                .key(file.getBlobId().getName())
                .type(file.getContentType())
                .url(file.getMediaLink())
                .build();
    }
}
