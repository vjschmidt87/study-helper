package com.portfolio.studyhelper.service;

import com.portfolio.studyhelper.dto.request.UpdateProgressRequest;
import com.portfolio.studyhelper.dto.response.DashboardResponse;
import com.portfolio.studyhelper.dto.response.ModuleProgressResponse;
import com.portfolio.studyhelper.dto.response.StudyProgressResponse;
import com.portfolio.studyhelper.entity.StudyProgress;
import com.portfolio.studyhelper.entity.Topic;
import com.portfolio.studyhelper.entity.User;
import com.portfolio.studyhelper.enums.StudyStatus;
import com.portfolio.studyhelper.exception.ResourceNotFoundException;
import com.portfolio.studyhelper.repository.ModuleRepository;
import com.portfolio.studyhelper.repository.StudyProgressRepository;
import com.portfolio.studyhelper.repository.TopicRepository;
import com.portfolio.studyhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final StudyProgressRepository studyProgressRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;

    @Transactional(readOnly = true)
    public List<StudyProgressResponse> getUserProgress(Long userId) {
        return studyProgressRepository.findByUserIdWithTopics(userId).stream()
                .map(StudyProgressResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public DashboardResponse getDashboard(Long userId) {
        int totalTopics = (int) topicRepository.count();
        int completedTopics = (int) studyProgressRepository.countByUserIdAndStatus(userId, StudyStatus.COMPLETED);
        int inProgressTopics = (int) studyProgressRepository.countByUserIdAndStatus(userId, StudyStatus.IN_PROGRESS);
        double completionPercentage = totalTopics > 0 ? (double) completedTopics / totalTopics * 100 : 0;

        // Module-level progress
        List<StudyProgress> allProgress = studyProgressRepository.findByUserIdWithTopics(userId);
        Map<Long, List<StudyProgress>> progressByModule = allProgress.stream()
                .collect(Collectors.groupingBy(sp -> sp.getTopic().getModule().getId()));

        List<ModuleProgressResponse> moduleProgress = new ArrayList<>();
        moduleRepository.findAllByOrderByPositionAsc().forEach(module -> {
            int moduleTopics = module.getTopics() != null ? module.getTopics().size() : 0;
            List<StudyProgress> moduleProgressList = progressByModule.getOrDefault(module.getId(), List.of());
            int moduleCompleted = (int) moduleProgressList.stream()
                    .filter(sp -> sp.getStatus() == StudyStatus.COMPLETED)
                    .count();
            double modulePercentage = moduleTopics > 0 ? (double) moduleCompleted / moduleTopics * 100 : 0;

            moduleProgress.add(new ModuleProgressResponse(
                    module.getId(), module.getNumber(),
                    module.getTitleEn(), module.getTitlePt(),
                    moduleTopics, moduleCompleted, modulePercentage
            ));
        });

        // Recent activity
        List<StudyProgressResponse> recentActivity = studyProgressRepository
                .findRecentByUserId(userId, PageRequest.of(0, 5)).stream()
                .map(StudyProgressResponse::from)
                .toList();

        return new DashboardResponse(totalTopics, completedTopics, inProgressTopics,
                completionPercentage, moduleProgress, recentActivity);
    }

    @Transactional
    public StudyProgressResponse updateProgress(Long userId, Long topicId, UpdateProgressRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Topic topic = topicRepository.findByIdWithModule(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + topicId));

        StudyProgress progress = studyProgressRepository.findByUserIdAndTopicId(userId, topicId)
                .orElse(StudyProgress.builder()
                        .user(user)
                        .topic(topic)
                        .build());

        progress.setStatus(request.status());
        progress.setNotes(request.notes());

        if (request.status() == StudyStatus.IN_PROGRESS && progress.getStartedAt() == null) {
            progress.setStartedAt(LocalDateTime.now());
        }
        if (request.status() == StudyStatus.COMPLETED && progress.getCompletedAt() == null) {
            progress.setCompletedAt(LocalDateTime.now());
        }
        if (request.status() == StudyStatus.NOT_STARTED) {
            progress.setStartedAt(null);
            progress.setCompletedAt(null);
        }

        studyProgressRepository.save(progress);
        return StudyProgressResponse.from(progress);
    }
}
