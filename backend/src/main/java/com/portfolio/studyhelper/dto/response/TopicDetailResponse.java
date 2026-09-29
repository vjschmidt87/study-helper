package com.portfolio.studyhelper.dto.response;

import com.portfolio.studyhelper.entity.Topic;
import java.util.List;

public record TopicDetailResponse(
    Long id,
    String number,
    String titleEn,
    String titlePt,
    String conceptEn,
    String conceptPt,
    Long moduleId,
    int moduleNumber,
    String moduleTitleEn,
    String moduleTitlePt,
    List<ResourceResponse> resources,
    List<ExerciseResponse> exercises
) {
    public static TopicDetailResponse from(Topic t) {
        List<ResourceResponse> resourceList = t.getResources() != null
            ? t.getResources().stream().map(ResourceResponse::from).toList()
            : List.of();
        List<ExerciseResponse> exerciseList = t.getExercises() != null
            ? t.getExercises().stream().map(ExerciseResponse::from).toList()
            : List.of();
        return new TopicDetailResponse(
            t.getId(), t.getNumber(), t.getTitleEn(), t.getTitlePt(),
            t.getConceptEn(), t.getConceptPt(),
            t.getModule().getId(), t.getModule().getNumber(),
            t.getModule().getTitleEn(), t.getModule().getTitlePt(),
            resourceList, exerciseList
        );
    }
}
