package com.portfolio.studyhelper.dto.response;

import com.portfolio.studyhelper.entity.Topic;

public record TopicSummaryResponse(
    Long id,
    String number,
    String titleEn,
    String titlePt,
    int position
) {
    public static TopicSummaryResponse from(Topic t) {
        return new TopicSummaryResponse(t.getId(), t.getNumber(), t.getTitleEn(), t.getTitlePt(), t.getPosition());
    }
}
