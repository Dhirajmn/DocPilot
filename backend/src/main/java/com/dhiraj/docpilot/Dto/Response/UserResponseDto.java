package com.dhiraj.docpilot.Dto.Response;

import com.dhiraj.docpilot.Entity.User;

import java.util.UUID;

public record UserResponseDto(UUID id, String name, String email) {
    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
