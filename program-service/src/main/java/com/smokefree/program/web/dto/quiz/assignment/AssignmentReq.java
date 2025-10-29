package com.smokefree.program.web.dto.quiz.assignment;

import java.util.List;
import java.util.UUID;

public record AssignmentReq(UUID templateId, List<UUID> programIds, Integer everyDays) {}
