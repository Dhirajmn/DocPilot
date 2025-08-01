package com.dhiraj.docpilot.Controller;

import com.dhiraj.docpilot.Dto.Request.DocumentRequestDto;
import com.dhiraj.docpilot.Dto.Response.DocumentResponseDto;
import com.dhiraj.docpilot.Dto.ApiResponse;
import com.dhiraj.docpilot.Entity.Document;
import com.dhiraj.docpilot.Entity.User;
import com.dhiraj.docpilot.Repository.DocumentRepository;
import com.dhiraj.docpilot.Service.DocumentService;
import com.dhiraj.docpilot.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/docs")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final UserService userService;
    private final DocumentService documentService;

    private final UUID MOCK_USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    //create Document
    @PostMapping
    public ResponseEntity<ApiResponse<DocumentResponseDto>> createDocument(@RequestBody DocumentRequestDto dto) {
        User owner = userService.findById(MOCK_USER_ID);

        Document saved = documentService.createDocument(dto.getTitle(), dto.getContent(), owner);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Document created successfully", DocumentResponseDto.fromEntity(saved)));
    }

    //list all documents
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<DocumentResponseDto>>> myDocuments() {
        User owner = userService.findById(MOCK_USER_ID);

        List<Document> documentList = documentService.getDocuments(owner);
        List<DocumentResponseDto> dtos = documentList.stream()
                .map(DocumentResponseDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ApiResponse<>(true, "Documents fetched successfully", dtos));
    }

    //get document by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentResponseDto>> getDocument(@PathVariable UUID id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Document fetched successfully", DocumentResponseDto.fromEntity(document)));
    }

    //update document
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentResponseDto>> updateDocument(@PathVariable UUID id, @RequestBody DocumentRequestDto dto) {
        Document existing = documentService.getDocumentById(id);

        if (!existing.getOwner().getId().equals(MOCK_USER_ID)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this document");
        }

        Document saved = documentService.updateDocument(existing, dto.getTitle(), dto.getContent());
        return ResponseEntity.ok(new ApiResponse<>(true, "Document updated successfully", DocumentResponseDto.fromEntity(saved)));
    }

    //delete document
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDocument(@PathVariable UUID id) {
        documentService.deleteDocument(id, MOCK_USER_ID);
        return ResponseEntity.ok(new ApiResponse<>(true, "Document deleted successfully", null));
    }
}
