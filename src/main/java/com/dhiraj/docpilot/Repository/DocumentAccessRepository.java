package com.dhiraj.docpilot.Repository;

import com.dhiraj.docpilot.Entity.Document;
import com.dhiraj.docpilot.Entity.DocumentAccess;
import com.dhiraj.docpilot.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentAccessRepository extends JpaRepository<DocumentAccess, UUID> {
    List<DocumentAccess> findByUser(User owner);

    List<DocumentAccess> findByDocument(Document document);

    List<DocumentAccess> findByUserAndDocument(User owner, Document document);
}
