package com.smokefree.program.web.controller;

import com.smokefree.program.domain.service.QuizService;
import com.smokefree.program.web.dto.quiz.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/answers")
    public QuizAnswerRes submit(@RequestBody QuizAnswerReq req,
                                @RequestHeader(value="X-User-Id", required=false) UUID userId,
                                @RequestHeader(value="X-User-Tier", required=false) String tier) {
        return quizService.submitAnswers(userId, req, tier);
    }
}
