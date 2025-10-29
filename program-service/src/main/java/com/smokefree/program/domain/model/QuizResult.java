package com.smokefree.program.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "quiz_results", schema = "program")
@Getter
@Setter
public class QuizResult {
    @Id
    private UUID id;
    private UUID programId;
    private UUID templateId;
    private Integer quizVersion;
    private Integer totalScore;
    @Enumerated(EnumType.STRING) private SeverityLevel severity; // bạn đã có enum này
    private Instant createdAt;
}