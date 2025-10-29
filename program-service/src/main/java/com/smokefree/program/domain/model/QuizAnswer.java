package com.smokefree.program.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "quiz_answers", schema = "program")
@Getter
@Setter
public class QuizAnswer {
    @EmbeddedId
    private QuizAnswerId id;

    @MapsId("attemptId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id")
    private QuizAttempt attempt;

    private Integer answer;  // score đã chọn 1..5
    private Instant createdAt;
}
