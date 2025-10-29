package com.smokefree.program.domain.service.quiz.impl.quiz;


import com.smokefree.program.domain.model.SeverityLevel;
import com.smokefree.program.domain.service.QuizService;
import com.smokefree.program.web.dto.quiz.*;
import com.smokefree.program.web.error.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuizServiceImpl implements QuizService {

    @Override
    public QuizAnswerRes submitAnswers(UUID userId, QuizAnswerReq req, String userTier) {
        if (req.answers() == null || req.answers().size() != 10)
            throw new ValidationException("Must provide exactly 10 answers");
        int total = req.answers().stream().mapToInt(a -> {
            if (a.score() < 1 || a.score() > 5)
                throw new ValidationException("Score must be 1..5 at q=" + a.q());
            return a.score();
        }).sum();

        SeverityLevel sev = mapSeverity(total);
        int plan = recommendPlanDays(sev);
        String recTier = switch (sev) { case LOW -> "basic"; case MODERATE -> "premium"; case HIGH -> "vip"; };
        List<String> alts = switch (sev) { case LOW -> List.of("premium"); case MODERATE -> List.of("vip"); case HIGH -> List.of("premium"); };
        String reason = switch (sev) { case LOW -> "Mức thấp — đủ Basic"; case MODERATE -> "Trung bình — nên Premium"; case HIGH -> "Cao — cần VIP kèm coach"; };

        return new QuizAnswerRes(total, sev, plan, new Recommendation(recTier, alts, reason), new Trial(true, 7));
    }

    @Override public SeverityLevel mapSeverity(int total) {
        if (total <= 20) return SeverityLevel.LOW;
        if (total <= 35) return SeverityLevel.MODERATE;
        return SeverityLevel.HIGH;
    }
    @Override public int recommendPlanDays(SeverityLevel sev) {
        return switch (sev) { case LOW -> 30; case MODERATE -> 45; case HIGH -> 60; };
    }
}
