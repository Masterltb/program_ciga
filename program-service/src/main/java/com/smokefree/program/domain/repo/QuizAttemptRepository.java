package com.smokefree.program.domain.repo;

import com.smokefree.program.domain.model.AttemptStatus;
import com.smokefree.program.domain.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, UUID> {
    Optional<QuizAttempt> findFirstByProgramIdAndTemplateIdAndStatus(UUID programId, UUID templateId, AttemptStatus status);
    List<QuizAttempt> findByProgramIdAndTemplateIdOrderByOpenedAtDesc(UUID programId, UUID templateId);
}
