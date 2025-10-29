package com.smokefree.program.web.controller.quiz;

import com.smokefree.program.domain.service.quiz.QuizAssignmentService;
import com.smokefree.program.domain.service.quiz.QuizTemplateService;
import com.smokefree.program.web.dto.quiz.assignment.AssignmentReq;
import com.smokefree.program.web.dto.quiz.template.TemplateRes;
import com.smokefree.program.web.dto.quiz.template.TemplateUpsertReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class AdminQuizController {

    private final QuizTemplateService templateService;
    private final QuizAssignmentService assignmentService;

    @PostMapping("/templates")
    @PreAuthorize("hasRole('ADMIN')")
    public TemplateRes createSystem(@RequestBody @Valid TemplateUpsertReq req) {
        var t = templateService.createSystemTemplate(req, getUserId());
        return new TemplateRes(t.getId(), t.getName(), t.getVersion(), t.getStatus().name());
    }

    @PatchMapping("/templates/{id}/publish")
    @PreAuthorize("hasRole('ADMIN')")
    public TemplateRes publish(@PathVariable UUID id) {
        var t = templateService.publish(id, getUserId());
        return new TemplateRes(t.getId(), t.getName(), t.getVersion(), t.getStatus().name());
    }

    @PostMapping("/assignments")
    @PreAuthorize("hasRole('ADMIN')")
    public void assign(@RequestBody @Valid AssignmentReq req) {
        assignmentService.assignToPrograms(
                req.templateId(),
                req.programIds(),
                req.everyDays() == null ? 5 : req.everyDays(),
                getUserId(),
                "system"
        );
    }

    private UUID getUserId() { return com.smokefree.program.util.SecurityUtil.currentUserId(); }
}

