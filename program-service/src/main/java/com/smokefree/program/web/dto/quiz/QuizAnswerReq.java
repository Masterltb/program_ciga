package com.smokefree.program.web.dto.quiz;

import java.util.List;
import java.util.UUID;

public record QuizAnswerReq(UUID templateId, List<QuizAnswerItem> answers) {}
