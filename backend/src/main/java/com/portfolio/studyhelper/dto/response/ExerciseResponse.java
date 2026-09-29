package com.portfolio.studyhelper.dto.response;

import com.portfolio.studyhelper.entity.Exercise;

public record ExerciseResponse(
    Long id,
    String descriptionEn,
    String descriptionPt,
    int position
) {
    public static ExerciseResponse from(Exercise e) {
        return new ExerciseResponse(e.getId(), e.getDescriptionEn(), e.getDescriptionPt(), e.getPosition());
    }
}
