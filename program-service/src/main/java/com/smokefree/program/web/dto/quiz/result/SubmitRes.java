package com.smokefree.program.web.dto.quiz.result;

import java.util.UUID;

public record SubmitRes(UUID attemptId, Integer totalScore, String severity) {}
