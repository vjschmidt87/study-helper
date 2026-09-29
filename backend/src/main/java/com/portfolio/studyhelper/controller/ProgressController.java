package com.portfolio.studyhelper.controller;

import com.portfolio.studyhelper.dto.request.UpdateProgressRequest;
import com.portfolio.studyhelper.dto.response.DashboardResponse;
import com.portfolio.studyhelper.dto.response.StudyProgressResponse;
import com.portfolio.studyhelper.entity.User;
import com.portfolio.studyhelper.exception.ResourceNotFoundException;
import com.portfolio.studyhelper.repository.UserRepository;
import com.portfolio.studyhelper.service.ProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<StudyProgressResponse>> getUserProgress() {
        Long userId = getDefaultUserId();
        return ResponseEntity.ok(progressService.getUserProgress(userId));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {
        Long userId = getDefaultUserId();
        return ResponseEntity.ok(progressService.getDashboard(userId));
    }

    @PutMapping("/topics/{topicId}")
    public ResponseEntity<StudyProgressResponse> updateProgress(
            @PathVariable Long topicId,
            @Valid @RequestBody UpdateProgressRequest request) {
        Long userId = getDefaultUserId();
        return ResponseEntity.ok(progressService.updateProgress(userId, topicId, request));
    }

    private Long getDefaultUserId() {
        User user = userRepository.findByUsername("default")
                .orElseThrow(() -> new ResourceNotFoundException("Default user not found"));
        return user.getId();
    }
}
