package com.smokefree.program.web.dto.quiz;


import com.smokefree.program.domain.model.SeverityLevel;

public record QuizAnswerRes(
        int total,
        SeverityLevel severity,
        int recommendedPlanDays,
        Recommendation recommendation,
        Trial trial) {}
