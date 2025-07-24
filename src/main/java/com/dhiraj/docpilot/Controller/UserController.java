package com.dhiraj.docpilot.Controller;

import com.dhiraj.docpilot.Dto.UserDto;
import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody User user) {
        User saved =  userRepository.save(user);
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }

}
