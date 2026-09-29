package com.portfolio.studyhelper.service;

import com.portfolio.studyhelper.dto.response.ModuleResponse;
import com.portfolio.studyhelper.exception.ResourceNotFoundException;
import com.portfolio.studyhelper.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;

    @Transactional(readOnly = true)
    public List<ModuleResponse> getAllModules() {
        return moduleRepository.findAllWithTopics().stream()
                .map(ModuleResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ModuleResponse getModuleById(Long id) {
        return moduleRepository.findById(id)
                .map(ModuleResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + id));
    }
}
