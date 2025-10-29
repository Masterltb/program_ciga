package com.smokefree.program.domain.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class QuizTemplateQuestionId implements Serializable {
    private UUID templateId;
    private Integer questionNo;
}