package com.dhiraj.docpilot.Service;

import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public boolean emailExists(String email) {
        return userRepo.existsByEmail(email);
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public User findById(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

}
