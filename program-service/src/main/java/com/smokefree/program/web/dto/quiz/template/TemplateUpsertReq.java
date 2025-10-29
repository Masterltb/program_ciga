package com.smokefree.program.web.dto.quiz.template;

import java.util.List;

public record TemplateUpsertReq(
        String name,
        String languageCode,
        List<QuestionReq> questions
) {
    public record QuestionReq(Integer questionNo, String text, List<ChoiceReq> choices) {}
    public record ChoiceReq(Integer score, String label) {}
}
