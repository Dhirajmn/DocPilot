package com.dhiraj.docpilot.Repository;

import com.dhiraj.docpilot.Entity.Document;
import com.dhiraj.docpilot.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findByOwner(User owner);
}
