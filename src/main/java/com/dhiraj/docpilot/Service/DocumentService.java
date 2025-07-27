package com.dhiraj.docpilot.Service;

import com.dhiraj.docpilot.Entity.Document;
import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public Document createDocument(String title, String content, User owner) {
        Document document = Document
                .builder()
                .title(title)
                .content(content)
                .owner(owner)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return documentRepository.save(document);
    }

    public List<Document> getDocuments(User owner) {
        return documentRepository.findByOwner(owner);
    }

    public Document getDocumentById(UUID id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));
    }

    public Document updateDocument(Document existing, String title, String content) {
        existing.setTitle(title);
        existing.setContent(content);
        existing.setUpdatedAt(LocalDateTime.now());
        return documentRepository.save(existing);
    }

    public void deleteDocument(UUID docId, UUID userId) {
        Document doc = getDocumentById(docId);

        if (!doc.getOwner().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this document");
        }
        documentRepository.deleteById(docId);
    }
}
