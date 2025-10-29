package com.smokefree.program.web.dto.quiz.attempt;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record OpenAttemptRes(UUID attemptId, UUID templateId, Integer version, List<QuestionView> questions) {
    public record QuestionView(Integer questionNo, String text, Map<Integer, String> choices) {}
}
