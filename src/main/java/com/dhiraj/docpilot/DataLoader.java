package com.dhiraj.docpilot;

import com.dhiraj.docpilot.Entity.Document;
import com.dhiraj.docpilot.Entity.DocumentAccess;
import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Repository.DocumentAccessRepository;
import com.dhiraj.docpilot.Repository.DocumentRepository;
import com.dhiraj.docpilot.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    CommandLineRunner run(
            UserRepository userRepo,
            DocumentRepository docRepo,
            DocumentAccessRepository accessRepo
    ) {
        return args -> {
            //Create 2 Users
            User user1 = User.builder()
                    .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                    .username("Alice")
                    .email("alice@example.com")
                    .password("pass1")
                    .build();

            User user2 = User.builder()
                    .id(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                    .username("Bob")
                    .email("bob@example.com")
                    .password("pass2")
                    .build();

            userRepo.save(user1);
            userRepo.save(user2);

            //Create 2 Documents by Alice
            Document doc1 = Document
                    .builder()
                    .title("Spring Boot Notes")
                    .content("Content of spring notes")
                    .owner(user1)
                    .build();

            Document doc2 = Document
                    .builder()
                    .title("Java Guide")
                    .content("Java basics and advanced")
                    .owner(user1)
                    .build();

            doc1 = docRepo.save(doc1);
            doc2 = docRepo.save(doc2);

            //Share doc2 with Bob (READ access)
            DocumentAccess access = DocumentAccess.builder()
                    .document(doc2)
                    .user(user2)
                    .accessType(DocumentAccess.AccessType.READ)
                    .build();

            accessRepo.save(access);

            System.out.println("📦 Dummy data loaded!");
        };
    }
}
