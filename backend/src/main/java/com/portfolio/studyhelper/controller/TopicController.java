package com.portfolio.studyhelper.controller;

import com.portfolio.studyhelper.dto.response.TopicDetailResponse;
import com.portfolio.studyhelper.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailResponse> getTopicDetail(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.getTopicDetail(id));
    }
}
