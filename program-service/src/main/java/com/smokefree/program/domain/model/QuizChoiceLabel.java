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

    @MapsId("templateId") // map phần templateId/questionNo trong ID vào quan hệ
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "template_id", referencedColumnName = "template_id"),
            @JoinColumn(name = "question_no", referencedColumnName = "question_no")
    })
    private QuizTemplateQuestion question;

    @Column(name = "label", nullable = false)
    private String label;    // “≤ 5 phút”, “6–15 phút”,…
    // KHÔNG khai báo lại 'score' ở đây – đã nằm trong id
}
