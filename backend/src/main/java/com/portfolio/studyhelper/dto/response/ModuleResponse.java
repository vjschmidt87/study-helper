package com.portfolio.studyhelper.dto.response;

import com.portfolio.studyhelper.entity.Module;
import java.util.List;

public record ModuleResponse(
    Long id,
    int number,
    String titleEn,
    String titlePt,
    String descriptionEn,
    String descriptionPt,
    String objectiveEn,
    String objectivePt,
    int weekStart,
    int weekEnd,
    List<TopicSummaryResponse> topics
) {
    public static ModuleResponse from(Module m) {
        List<TopicSummaryResponse> topicList = m.getTopics() != null
            ? m.getTopics().stream().map(TopicSummaryResponse::from).toList()
            : List.of();
        return new ModuleResponse(
            m.getId(), m.getNumber(), m.getTitleEn(), m.getTitlePt(),
            m.getDescriptionEn(), m.getDescriptionPt(),
            m.getObjectiveEn(), m.getObjectivePt(),
            m.getWeekStart(), m.getWeekEnd(), topicList
        );
    }
}
