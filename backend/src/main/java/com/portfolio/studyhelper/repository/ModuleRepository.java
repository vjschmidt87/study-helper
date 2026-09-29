package com.portfolio.studyhelper.repository;

import com.portfolio.studyhelper.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findAllByOrderByPositionAsc();

    @Query("SELECT DISTINCT m FROM Module m LEFT JOIN FETCH m.topics t ORDER BY m.position, t.position")
    List<Module> findAllWithTopics();
}
