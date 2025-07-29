package com.dhiraj.docpilot.Service;

import com.dhiraj.docpilot.Entity.Document;
import com.dhiraj.docpilot.Entity.DocumentAccess;
import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Exception.ResourceNotFoundException;
import com.dhiraj.docpilot.Repository.DocumentAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentAccessService {

    private final DocumentAccessRepository documentAccessRepository;

    public List<DocumentAccess> findByDocument(Document document) {
        return documentAccessRepository.findByDocument(document);
    }

    public DocumentAccess findByUserAndDocument(User owner, Document document) {
        return documentAccessRepository.findByUserAndDocument(owner, document)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Access not found"));
    }
    
    public void revokeAccess(DocumentAccess access) {
        documentAccessRepository.delete(access);
    }

    public void shareDocument(User targetUser, Document document, DocumentAccess.AccessType accessType) {

        boolean alreadyShared = documentAccessRepository.findByUserAndDocument(targetUser, document).isPresent();

        if (alreadyShared) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Document already shared with this user");
        }

        DocumentAccess access = DocumentAccess.builder()
                .document(document)
                .user(targetUser)
                .accessType(accessType)
                .build();

        documentAccessRepository.save(access);
    }
}
