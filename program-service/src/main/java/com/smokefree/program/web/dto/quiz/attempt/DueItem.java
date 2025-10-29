package com.smokefree.program.web.dto.quiz.attempt;

import java.time.Instant;
import java.util.UUID;

public record DueItem(UUID templateId, UUID programId, String templateName, Instant dueAt) {}
