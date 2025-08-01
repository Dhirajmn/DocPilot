package com.dhiraj.docpilot.Dto.Response;

import com.dhiraj.docpilot.Entity.Document;

import java.util.UUID;

public record DocumentResponseDto(UUID id, String title, String content, String ownerUsername) {
    public static DocumentResponseDto fromEntity(Document document) {
        return new DocumentResponseDto(
                document.getId(),
                document.getTitle(),
                document.getContent(),
                document.getOwner().getUsername()
        );
    }
}
