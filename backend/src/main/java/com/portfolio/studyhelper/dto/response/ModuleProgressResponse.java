package com.portfolio.studyhelper.dto.response;

public record ModuleProgressResponse(
    Long moduleId,
    int moduleNumber,
    String titleEn,
    String titlePt,
    int totalTopics,
    int completedTopics,
    double completionPercentage
) {}
