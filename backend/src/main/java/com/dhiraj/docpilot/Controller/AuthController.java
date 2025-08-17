package com.dhiraj.docpilot.Controller;

import com.dhiraj.docpilot.Dto.ApiResponse;
import com.dhiraj.docpilot.Dto.Request.UserRequestDto;
import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Security.JwtUtil;
import com.dhiraj.docpilot.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody UserRequestDto dto) {
        if (userService.emailExists(dto.getEmail())) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Email already exists"), HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(dto);
        return new ResponseEntity<>(new ApiResponse<>(true, "User registered successfully"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody UserRequestDto dto) {
        return userService.authenticate(dto)
                .map(user -> {
                    String token = jwtUtil.generateToken(user.getEmail());
                    return new ResponseEntity<>(
                            new ApiResponse<>(true, "Logged in successfully", token),
                            HttpStatus.OK
                    );
                })
                .orElse(new ResponseEntity<>(
                        new ApiResponse<>(false, "Invalid email or password"),
                        HttpStatus.UNAUTHORIZED
                ));
    }

}



