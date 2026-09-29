package com.portfolio.studyhelper.dto.request;

import com.portfolio.studyhelper.enums.StudyStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateProgressRequest(
    @NotNull StudyStatus status,
    String notes
) {}
