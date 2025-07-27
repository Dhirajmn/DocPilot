package com.dhiraj.docpilot.Dto.Request;

import com.dhiraj.docpilot.Entity.Document;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentRequestDto {

    @NotBlank(message = "title cannot be blank")
    private String title;

    private String content;

    public static DocumentRequestDto toEntity(Document document) {
        return new DocumentRequestDto(document.getTitle(), document.getContent());
    }
}
