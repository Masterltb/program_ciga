package com.smokefree.program.web.dto.quiz;

import java.util.List;

public record Recommendation(String tier, List<String> alternatives, String reason) {}
