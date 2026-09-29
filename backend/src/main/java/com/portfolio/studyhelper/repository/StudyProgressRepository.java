package com.portfolio.studyhelper.repository;

import com.portfolio.studyhelper.entity.StudyProgress;
import com.portfolio.studyhelper.enums.StudyStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface StudyProgressRepository extends JpaRepository<StudyProgress, Long> {
    Optional<StudyProgress> findByUserIdAndTopicId(Long userId, Long topicId);
    List<StudyProgress> findByUserId(Long userId);

    @Query("SELECT sp FROM StudyProgress sp JOIN FETCH sp.topic t JOIN FETCH t.module WHERE sp.user.id = :userId")
    List<StudyProgress> findByUserIdWithTopics(@Param("userId") Long userId);

    @Query("SELECT sp FROM StudyProgress sp JOIN FETCH sp.topic t JOIN FETCH t.module WHERE sp.user.id = :userId ORDER BY sp.updatedAt DESC")
    List<StudyProgress> findRecentByUserId(@Param("userId") Long userId, Pageable pageable);

    long countByUserIdAndStatus(Long userId, StudyStatus status);
}
