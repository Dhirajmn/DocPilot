package com.dhiraj.docpilot.Controller;

import com.dhiraj.docpilot.Dto.ApiResponse;
import com.dhiraj.docpilot.Dto.DocumentAccessResponseDto;
import com.dhiraj.docpilot.Entity.Document;
import com.dhiraj.docpilot.Entity.DocumentAccess;
import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Service.DocumentAccessService;
import com.dhiraj.docpilot.Service.DocumentService;
import com.dhiraj.docpilot.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/docs")
@RequiredArgsConstructor
public class DocumentAccessController {

    private final UserService userService;
    private final DocumentService documentService;
    private final DocumentAccessService documentAccessService;

    private final UUID MOCK_USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    // Share document with user
    @PostMapping("/share")
    public ResponseEntity<ApiResponse<Void>> shareDocument(
            @RequestParam UUID documentId,
            @RequestParam String targetMail,
            @RequestParam DocumentAccess.AccessType accessType
    ) {
        User owner = userService.findById(MOCK_USER_ID);

        Document doc = documentService.getDocumentById(documentId);

        if (!doc.getOwner().equals(owner)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't own this document");
        }

        User targetUser = userService.findByEmail(targetMail);

        documentAccessService.shareDocument(targetUser, doc, accessType);

        return ResponseEntity.ok(new ApiResponse<>(true, "Document shared with " + targetMail + " as " + accessType));
    }

    // List of users document is shared with
    @GetMapping("/shared-users/{docId}")
    public ResponseEntity<ApiResponse<List<DocumentAccessResponseDto>>> getSharedUsers(@PathVariable("docId") UUID documentId) {
        Document doc = documentService.getDocumentById(documentId);

        List<DocumentAccessResponseDto> sharedUsers = documentAccessService.findByDocument(doc)
                .stream()
                .map(DocumentAccessResponseDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ApiResponse<>(true, "Shared users fetched successfully", sharedUsers));
    }

    // Revoke access
    @DeleteMapping("/revoke")
    public ResponseEntity<ApiResponse<Void>> revokeAccess(
            @RequestParam UUID documentId,
            @RequestParam String targetMail
    ) {
        Document doc = documentService.getDocumentById(documentId);

        User targetUser = userService.findByEmail(targetMail);

        DocumentAccess access = documentAccessService.findByUserAndDocument(targetUser, doc);

        documentAccessService.revokeAccess(access);

        return ResponseEntity.ok(new ApiResponse<>(true, "Access revoked for user: " + targetMail));
    }
}
