package com.portfolio.studyhelper.dto.response;

import com.portfolio.studyhelper.entity.StudyNote;
import java.time.LocalDateTime;

public record StudyNoteResponse(
    Long id,
    Long topicId,
    String topicTitleEn,
    String topicTitlePt,
    String title,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static StudyNoteResponse from(StudyNote n) {
        return new StudyNoteResponse(
            n.getId(),
            n.getTopic() != null ? n.getTopic().getId() : null,
            n.getTopic() != null ? n.getTopic().getTitleEn() : null,
            n.getTopic() != null ? n.getTopic().getTitlePt() : null,
            n.getTitle(),
            n.getContent(),
            n.getCreatedAt(),
            n.getUpdatedAt()
        );
    }
}
