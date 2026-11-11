package com.smokefree.program.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "quiz_choice_labels", schema = "program")
@Getter @Setter
public class QuizChoiceLabel {

    @EmbeddedId
    private QuizChoiceLabelId id;

    // The embedded key owns both FK columns; this relation is read-only.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "template_id", referencedColumnName = "template_id", insertable = false, updatable = false),
            @JoinColumn(name = "question_no", referencedColumnName = "question_no", insertable = false, updatable = false)
    })
    private QuizTemplateQuestion question;

    @Column(name = "label", nullable = false)
    private String label;    // “≤ 5 phút”, “6–15 phút”,…
    // KHÔNG khai báo lại 'score' ở đây – đã nằm trong id
}
