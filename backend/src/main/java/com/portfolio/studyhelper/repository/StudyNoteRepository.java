package com.portfolio.studyhelper.repository;

import com.portfolio.studyhelper.entity.StudyNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface StudyNoteRepository extends JpaRepository<StudyNote, Long> {

    @Query("SELECT n FROM StudyNote n LEFT JOIN FETCH n.topic WHERE n.user.id = :userId ORDER BY n.updatedAt DESC")
    List<StudyNote> findByUserIdWithTopics(@Param("userId") Long userId);

    @Query("SELECT n FROM StudyNote n LEFT JOIN FETCH n.topic WHERE n.user.id = :userId AND n.topic.id = :topicId ORDER BY n.updatedAt DESC")
    List<StudyNote> findByUserIdAndTopicId(@Param("userId") Long userId, @Param("topicId") Long topicId);

    Optional<StudyNote> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT n FROM StudyNote n LEFT JOIN FETCH n.topic WHERE n.user.id = :userId AND (LOWER(n.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(n.content) LIKE LOWER(CONCAT('%', :search, '%'))) ORDER BY n.updatedAt DESC")
    List<StudyNote> searchByUserId(@Param("userId") Long userId, @Param("search") String search);
}
