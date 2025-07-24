package com.dhiraj.docpilot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentAccess {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Document document;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    public enum AccessType {
        READ, WRITE
    }
}
