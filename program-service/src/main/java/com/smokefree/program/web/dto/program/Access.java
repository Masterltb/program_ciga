package com.smokefree.program.web.dto.program;

import java.time.Instant;

public record Access(String state, Instant expiresAt, String tier) {}
