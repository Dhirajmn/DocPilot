package com.dhiraj.docpilot.Dto;

import com.dhiraj.docpilot.Entity.DocumentAccess;

import java.util.UUID;

public record DocumentAccessResponseDto(
        UUID userId,
        String username,
        String email,
        DocumentAccess.AccessType accessType
) {
    public static DocumentAccessResponseDto fromEntity(DocumentAccess access) {
        return new DocumentAccessResponseDto(
                access.getUser().getId(),
                access.getUser().getUsername(),
                access.getUser().getEmail(),
                access.getAccessType()
        );
    }
}
