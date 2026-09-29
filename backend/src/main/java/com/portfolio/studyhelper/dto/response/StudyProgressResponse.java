package com.portfolio.studyhelper.dto.response;

import com.portfolio.studyhelper.entity.StudyProgress;
import java.time.LocalDateTime;

public record StudyProgressResponse(
    Long id,
    Long topicId,
    String topicNumber,
    String topicTitleEn,
    String topicTitlePt,
    Long moduleId,
    int moduleNumber,
    String status,
    String notes,
    LocalDateTime startedAt,
    LocalDateTime completedAt,
    LocalDateTime updatedAt
) {
    public static StudyProgressResponse from(StudyProgress sp) {
        return new StudyProgressResponse(
            sp.getId(),
            sp.getTopic().getId(),
            sp.getTopic().getNumber(),
            sp.getTopic().getTitleEn(),
            sp.getTopic().getTitlePt(),
            sp.getTopic().getModule().getId(),
            sp.getTopic().getModule().getNumber(),
            sp.getStatus().name(),
            sp.getNotes(),
            sp.getStartedAt(),
            sp.getCompletedAt(),
            sp.getUpdatedAt()
        );
    }
}
