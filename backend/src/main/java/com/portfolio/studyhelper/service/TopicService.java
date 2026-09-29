package com.portfolio.studyhelper.service;

import com.portfolio.studyhelper.dto.response.TopicDetailResponse;
import com.portfolio.studyhelper.entity.Topic;
import com.portfolio.studyhelper.exception.ResourceNotFoundException;
import com.portfolio.studyhelper.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public TopicDetailResponse getTopicDetail(Long id) {
        Topic topic = topicRepository.findByIdWithModule(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + id));

        topic.getResources().size();
        topic.getExercises().size();

        return TopicDetailResponse.from(topic);
    }
}
