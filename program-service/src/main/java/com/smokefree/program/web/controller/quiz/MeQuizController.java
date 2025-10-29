package com.smokefree.program.web.controller.quiz;

import com.smokefree.program.domain.service.quiz.QuizFlowService;
import com.smokefree.program.web.dto.quiz.answer.AnswerReq;
import com.smokefree.program.web.dto.quiz.attempt.DueItem;
import com.smokefree.program.web.dto.quiz.attempt.OpenAttemptRes;
import com.smokefree.program.web.dto.quiz.result.SubmitRes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/me/quiz")
@RequiredArgsConstructor
public class MeQuizController {

    private final QuizFlowService flow;

    @GetMapping("/due")
    @PreAuthorize("isAuthenticated()")
    public List<DueItem> due() {
        return flow.listDue(getUserId());
    }

    @PostMapping("/{templateId}/attempts")
    @PreAuthorize("isAuthenticated()")
    public OpenAttemptRes open(@PathVariable UUID templateId) {
        return flow.openAttempt(getUserId(), templateId);
    }

    @PostMapping("/{templateId}/answers")
    @PreAuthorize("isAuthenticated()")
    public void answer(@PathVariable UUID templateId,
                       @RequestParam UUID attemptId,
                       @RequestBody @Valid AnswerReq req) {
        flow.saveAnswer(getUserId(), templateId, attemptId, req);
    }

    @PostMapping("/{templateId}/submit")
    @PreAuthorize("isAuthenticated()")
    public SubmitRes submit(@PathVariable UUID templateId,
                            @RequestParam UUID attemptId) {
        return flow.submit(getUserId(), templateId, attemptId);
    }

    private UUID getUserId() { return com.smokefree.program.util.SecurityUtil.currentUserId(); }
}

