package com.dhiraj.docpilot.Dto;

import java.util.UUID;

public record UserDto(UUID id, String name, String email) {
}
