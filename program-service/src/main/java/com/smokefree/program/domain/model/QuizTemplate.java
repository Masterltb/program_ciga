package com.smokefree.program.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// QuizTemplate.java  (đã có – bổ sung thuộc tính)
@Entity
@Table(name = "quiz_templates", schema = "program")
@Getter
@Setter
public class QuizTemplate {
    @Id
    private UUID id;
    private String name;
    private Integer version;
    @Enumerated(EnumType.STRING) private QuizTemplateStatus status; // DRAFT/PUBLISHED/ARCHIVED
    private String languageCode;
    private Instant publishedAt;
    private Instant archivedAt;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("questionNo ASC")
    private List<QuizTemplateQuestion> questions = new ArrayList<>();
}


