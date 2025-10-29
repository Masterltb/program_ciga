package com.smokefree.program.web.dto.quiz.template;

import java.util.UUID;

public record TemplateRes(UUID id, String name, Integer version, String status) {}
