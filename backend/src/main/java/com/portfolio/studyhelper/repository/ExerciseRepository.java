package com.portfolio.studyhelper.repository;

import com.portfolio.studyhelper.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByTopicIdOrderByPositionAsc(Long topicId);
}
