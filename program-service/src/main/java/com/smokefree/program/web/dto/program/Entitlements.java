package com.smokefree.program.web.dto.program;

import java.util.List;

public record Entitlements(String tier, List<String> features) {}