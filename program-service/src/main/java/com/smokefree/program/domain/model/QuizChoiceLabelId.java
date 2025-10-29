package com.smokefree.program.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter @Setter
public class QuizChoiceLabelId implements Serializable {
    @Column(name = "template_id", nullable = false)
    private UUID templateId;

    @Column(name = "question_no", nullable = false)
    private Integer questionNo;

    @Column(name = "score", nullable = false)
    private Integer score;
}
