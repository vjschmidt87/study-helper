package com.portfolio.studyhelper.dto.response;

import java.util.List;

public record DashboardResponse(
    int totalTopics,
    int completedTopics,
    int inProgressTopics,
    double completionPercentage,
    List<ModuleProgressResponse> moduleProgress,
    List<StudyProgressResponse> recentActivity
) {}
