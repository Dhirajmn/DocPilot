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

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(@Valid @RequestBody UserRequestDto dto) {

        if(userService.emailExists(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        User saved =  userService.saveUser(user);

        ApiResponse<UserResponseDto> response= new ApiResponse<>(true, "User registered successfully", UserResponseDto.fromEntity(saved));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser(@PathVariable UUID id) {
        User user = userService.findById(id);
        ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User fetched successfully", UserResponseDto.fromEntity(user));
        return ResponseEntity.ok(response);
    }
}
