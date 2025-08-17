package com.dhiraj.docpilot.Controller;

import com.dhiraj.docpilot.Dto.ApiResponse;
import com.dhiraj.docpilot.Dto.Request.UserRequestDto;
import com.dhiraj.docpilot.Dto.Response.UserResponseDto;
import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Repository.UserRepository;
import com.dhiraj.docpilot.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser(@PathVariable UUID id) {
        User user = userService.findById(id);
        ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User fetched successfully", UserResponseDto.fromEntity(user));
        return ResponseEntity.ok(response);
    }
}
