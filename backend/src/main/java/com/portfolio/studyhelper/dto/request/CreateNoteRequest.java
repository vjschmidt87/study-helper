package com.portfolio.studyhelper.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateNoteRequest(
    @NotBlank @Size(max = 200) String title,
    @NotBlank String content,
    Long topicId
) {}
