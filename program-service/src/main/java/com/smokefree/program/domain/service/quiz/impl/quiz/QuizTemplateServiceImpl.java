package com.smokefree.program.domain.service.quiz.impl.quiz;


import com.smokefree.program.domain.model.*;
import com.smokefree.program.domain.repo.QuizTemplateRepository;
import com.smokefree.program.domain.service.quiz.QuizTemplateService;
import com.smokefree.program.web.dto.quiz.template.TemplateUpsertReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizTemplateServiceImpl implements QuizTemplateService {

    private final QuizTemplateRepository templateRepo;

    @Override
    public QuizTemplate createSystemTemplate(TemplateUpsertReq req, UUID adminId) {
        return createTemplate(req, "system", adminId);
    }

    @Override
    public QuizTemplate createCoachTemplate(TemplateUpsertReq req, UUID coachId) {
        return createTemplate(req, "coach", coachId);
    }

    private QuizTemplate createTemplate(TemplateUpsertReq req, String scope, UUID ownerId) {
        QuizTemplate t = new QuizTemplate();
        t.setId(UUID.randomUUID());
        t.setName(req.name());
        t.setLanguageCode(req.languageCode());
        t.setScope(scope);                 // TODO: nếu entity của bạn không có field scope, bỏ dòng này
        t.setOwnerId(ownerId);             // TODO: nếu không có ownerId, bỏ
        t.setVersion(1);
        t.setStatus(QuizTemplateStatus.DRAFT);
        t.setCreatedAt(Instant.now());
        t.setUpdatedAt(Instant.now());

        // Questions
        var questions = new LinkedHashSet<QuizTemplateQuestion>();
        if (req.questions() != null) {
            for (var q : req.questions()) {
                QuizTemplateQuestion qq = new QuizTemplateQuestion();
                QuizTemplateQuestionId qid = new QuizTemplateQuestionId();
                qid.setTemplateId(t.getId());
                qid.setQuestionNo(q.questionNo());
                qq.setId(qid);
                qq.setTemplate(t);
                qq.setText(q.text());

                var choices = new LinkedHashSet<QuizChoiceLabel>();
                if (q.choices() != null) {
                    for (var c : q.choices()) {
                        QuizChoiceLabel cc = new QuizChoiceLabel();
                        QuizChoiceLabelId cid = new QuizChoiceLabelId();
                        cid.setTemplateId(t.getId());
                        cid.setQuestionNo(q.questionNo());
                        cid.setScore(c.score());
                        cc.setId(cid);
                        cc.setTemplate(t);
                        cc.setLabel(c.label());
                        choices.add(cc);
                    }
                }
                qq.setChoices(choices);
                questions.add(qq);
            }
        }
        t.setQuestions(questions);
        return templateRepo.save(t);
    }

    @Override
    public QuizTemplate publish(UUID templateId, UUID actorId) {
        QuizTemplate t = templateRepo.findById(templateId).orElseThrow();
        t.setStatus(QuizTemplateStatus.PUBLISHED);
        t.setPublishedAt(Instant.now());
        t.setUpdatedAt(Instant.now());
        return templateRepo.save(t);
    }

    @Override
    public QuizTemplate cloneFromGlobal(UUID templateId, UUID coachId) {
        QuizTemplate src = templateRepo.findById(templateId).orElseThrow();
        // chỉ cho clone template global (scope=system) nếu có
        // TODO: nếu không có field scope thì bỏ validate này
        if (!"system".equalsIgnoreCase(src.getScope())) {
            return src; // or throw
        }

        TemplateUpsertReq req = new TemplateUpsertReq(
                src.getName() + " (Coach Copy)",
                src.getLanguageCode(),
                src.getQuestions().stream().map(q ->
                        new TemplateUpsertReq.QuestionReq(
                                q.getId().getQuestionNo(),
                                q.getText(),
                                q.getChoices().stream().map(c ->
                                        new TemplateUpsertReq.ChoiceReq(c.getId().getScore(), c.getLabel())
                                ).toList()
                        )
                ).toList()
        );
        return createCoachTemplate(req, coachId);
    }
}