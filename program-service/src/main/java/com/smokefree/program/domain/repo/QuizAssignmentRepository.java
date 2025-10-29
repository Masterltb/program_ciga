package com.smokefree.program.domain.repo;

import com.smokefree.program.domain.model.QuizAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuizAssignmentRepository extends JpaRepository<QuizAssignment, UUID> {
    List<QuizAssignment> findByProgramId(UUID programId);
    boolean existsByTemplateIdAndProgramId(UUID templateId, UUID programId);
}
