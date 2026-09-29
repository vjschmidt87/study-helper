package com.portfolio.studyhelper.repository;

import com.portfolio.studyhelper.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByTopicIdOrderByPositionAsc(Long topicId);
}
