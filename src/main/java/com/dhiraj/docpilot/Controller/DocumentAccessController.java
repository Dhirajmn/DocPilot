package com.dhiraj.docpilot.Controller;

import com.dhiraj.docpilot.Entity.Document;
import com.dhiraj.docpilot.Entity.DocumentAccess;
import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Repository.DocumentAccessRepository;
import com.dhiraj.docpilot.Repository.DocumentRepository;
import com.dhiraj.docpilot.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/docs")
@RequiredArgsConstructor
public class DocumentAccessController {

    private final DocumentRepository documentRepository;
    private final DocumentAccessRepository documentAccessRepository;
    private final UserRepository userRepository;

    private final UUID MOCK_USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    //share doc with user
    @PostMapping("/share")
    public String shareDocument(
            @RequestParam UUID documentId,
            @RequestParam String targetMail,
            @RequestParam DocumentAccess.AccessType accessType
            ) {
        User owner = userRepository.findById(MOCK_USER_ID).orElseThrow();
        Document doc = documentRepository.findById(documentId).orElseThrow();

        if (!doc.getOwner().equals(owner)) {
            return "You don't own this document to share";
        }

        User targetUser = userRepository.findByEmail(targetMail).orElseThrow(() -> new RuntimeException("User not found"));

        if (documentAccessRepository.findByUserAndDocument(targetUser, doc).isPresent()) {
            return "Already shared with this user";
        }

        DocumentAccess access = DocumentAccess
                .builder()
                .document(doc)
                .user(targetUser)
                .accessType(DocumentAccess.AccessType.READ)
                .build();

        documentAccessRepository.save(access);
        return "Document shared with" + targetMail + " as " + accessType;
    }

    //list of users document is shared with
    @GetMapping("/shared-users/{docId}")
    public List<DocumentAccess> getSharedUser(@PathVariable UUID documentId) {
        Document doc = documentRepository.findById(documentId).orElseThrow();
        return documentAccessRepository.findByDocument(doc);
    }

    //Revoke access
    @DeleteMapping("/revoke")
    public String revokeAccess(
            @RequestParam UUID documentId,
            @RequestParam String targetMail
    ) {
        Document doc = documentRepository.findById(documentId).orElseThrow();
        User targetUser = userRepository.findByEmail(targetMail).orElseThrow();

        DocumentAccess access = documentAccessRepository.findByUserAndDocument(targetUser, doc).orElseThrow();

        documentAccessRepository.delete(access);
        return "Access revoked for user: " + targetMail;
    }



}
