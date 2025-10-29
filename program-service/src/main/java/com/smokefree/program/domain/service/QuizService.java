package com.smokefree.program.domain.service;

import com.smokefree.program.domain.model.SeverityLevel;
import com.smokefree.program.web.dto.quiz.*;

import java.util.UUID;

public interface QuizService {
    QuizAnswerRes submitAnswers(UUID userId, QuizAnswerReq req, String userTier);
    SeverityLevel mapSeverity(int total);
    int recommendPlanDays(SeverityLevel sev);
}
