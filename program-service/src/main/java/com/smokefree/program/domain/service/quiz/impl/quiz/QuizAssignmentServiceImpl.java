package com.smokefree.program.domain.service.quiz.impl.quiz;

import com.smokefree.program.domain.model.QuizAssignment;
import com.smokefree.program.domain.repo.ProgramRepository;
import com.smokefree.program.domain.repo.QuizAssignmentRepository;
import com.smokefree.program.domain.service.quiz.QuizAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizAssignmentServiceImpl implements QuizAssignmentService {

    private final QuizAssignmentRepository assignmentRepo;
    private final ProgramRepository programRepo;

    @Override
    public void assignToPrograms(UUID templateId, List<UUID> programIds, int everyDays, UUID actorId, String scope) {
        if (programIds == null || programIds.isEmpty()) return;

        List<QuizAssignment> batch = new ArrayList<>();
        for (UUID pid : programIds) {
            // quyền coach: xác minh coach là chủ program
            if ("coach".equals(scope)) {
                // cần method repo này (ở dưới mục Repository Methods)
                if (!programRepo.existsByIdAndCoachId(pid, actorId)) continue;
            }
            // tránh trùng assignment
            if (assignmentRepo.existsByTemplateIdAndProgramId(templateId, pid)) continue;

            QuizAssignment a = new QuizAssignment();
            a.setId(UUID.randomUUID());
            a.setTemplateId(templateId);
            a.setProgramId(pid);
            a.setEveryDays(everyDays);
            a.setCreatedAt(Instant.now());
            a.setCreatedBy(actorId); // nếu entity không có createdBy, bỏ
            a.setScope(scope);       // nếu entity không có scope, bỏ
            batch.add(a);
        }
        assignmentRepo.saveAll(batch);
    }

    @Override
    public List<QuizAssignment> listAssignmentsByProgram(UUID programId) {
        return assignmentRepo.findByProgramId(programId);
    }
}
