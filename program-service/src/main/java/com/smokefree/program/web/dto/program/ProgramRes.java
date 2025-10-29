package com.smokefree.program.web.dto.program;

import com.smokefree.program.domain.model.ProgramStatus;
import com.smokefree.program.domain.model.SeverityLevel;

import java.time.LocalDate;
import java.util.UUID;

public record ProgramRes(UUID id, ProgramStatus status, int planDays, LocalDate startDate, int currentDay,
                         SeverityLevel severity, Integer totalScore, Entitlements entitlements, Access access) {}