package com.smokefree.program.domain.repo;

import com.smokefree.program.domain.model.Program;
import com.smokefree.program.domain.model.ProgramStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProgramRepository extends JpaRepository<Program, UUID> {

    // dùng cho ProgramServiceImpl#createProgram và #getActive
    Optional<Program> findFirstByUserIdAndStatusAndDeletedAtIsNull(UUID userId, ProgramStatus status);

    // có thể giữ lại nếu cần cho nơi khác
    Optional<Program> findByUserId(UUID userId);

    // dùng khi coach cần xác thực quyền trên program
    boolean existsByIdAndCoachId(UUID id, UUID coachId);
}
