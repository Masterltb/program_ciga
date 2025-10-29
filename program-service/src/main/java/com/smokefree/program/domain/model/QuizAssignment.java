package com.smokefree.program.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "quiz_assignments", schema = "program")
@Getter
@Setter
public class QuizAssignment {
    @Id
    private UUID id;
    private UUID templateId;
    private UUID programId;        // null nếu là rule/filter (tùy bạn)
    private Integer everyDays;     // ví dụ =5
    private Instant createdAt;
    private UUID createdBy;        // admin/coach
    private String scope;          // "system" | "coach"
}
