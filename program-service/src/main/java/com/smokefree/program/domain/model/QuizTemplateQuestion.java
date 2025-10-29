package com.smokefree.program.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "quiz_template_questions", schema = "program")
@Getter
@Setter
public class QuizTemplateQuestion {
    @EmbeddedId
    private QuizTemplateQuestionId id;

    @MapsId("templateId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private QuizTemplate template;

    private String text;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("score ASC")
    private List<QuizChoiceLabel> choices = new ArrayList<>();
}
