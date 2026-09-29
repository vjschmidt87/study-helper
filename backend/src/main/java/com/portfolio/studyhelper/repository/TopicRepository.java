package com.portfolio.studyhelper.repository;

import com.portfolio.studyhelper.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByModuleIdOrderByPositionAsc(Long moduleId);

    @Query("SELECT t FROM Topic t LEFT JOIN FETCH t.resources LEFT JOIN FETCH t.exercises WHERE t.id = :id")
    Optional<Topic> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT t FROM Topic t JOIN FETCH t.module WHERE t.id = :id")
    Optional<Topic> findByIdWithModule(@Param("id") Long id);
}
