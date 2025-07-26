package com.dhiraj.docpilot.Controller;

import com.dhiraj.docpilot.Entity.Document;
import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Repository.DocumentRepository;
import com.dhiraj.docpilot.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/docs")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    // Mocked logged-in user for testing
    private final UUID MOCK_USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @PostMapping
    public Document createDocument(@RequestBody Document document) {
        User owner = userRepository.findById(MOCK_USER_ID).orElseThrow();
        document.setOwner(owner);
        return documentRepository.save(document);
    }

    @GetMapping("/my")
    public List<Document> myDocuments() {
        User owner = userRepository.findById(MOCK_USER_ID).orElseThrow();
        return documentRepository.findByOwner(owner);
    }

    @GetMapping("/{id}")
    public Document getDocument(@PathVariable UUID id) {
        return documentRepository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Document updateDocument(@PathVariable UUID id, @RequestBody Document updated) {
        Document doc = documentRepository.findById(id).orElseThrow();

        if (!doc.getOwner().getId().equals(MOCK_USER_ID)) {
            throw new RuntimeException("You are not the owner");
        }

        doc.setTitle(updated.getTitle());
        doc.setContent(updated.getContent());
        return documentRepository.save(doc);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable UUID id) {
        Document doc = documentRepository.findById(id).orElseThrow();

        if (!doc.getOwner().getId().equals(MOCK_USER_ID)) {
            throw new RuntimeException("You are not the owner");
        }

        documentRepository.delete(doc);
    }
}
