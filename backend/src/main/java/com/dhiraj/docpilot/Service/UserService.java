package com.dhiraj.docpilot.Service;

import com.dhiraj.docpilot.Dto.Request.UserRequestDto;
import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public boolean emailExists(String email) {
        return userRepo.existsByEmail(email);
    }

    public User saveUser(UserRequestDto dto) {
        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(hashedPassword)
                .build();
        return userRepo.save(user);
    }

    public User findById(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found"));
    }

    public Optional<User> authenticate(UserRequestDto dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );

            User user = userRepo.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

            return Optional.of(user);
        }
        catch (AuthenticationException e) {
            return Optional.empty();
        }
    }

}
