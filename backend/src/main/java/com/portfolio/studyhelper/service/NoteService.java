package com.portfolio.studyhelper.service;

import com.portfolio.studyhelper.dto.request.CreateNoteRequest;
import com.portfolio.studyhelper.dto.request.UpdateNoteRequest;
import com.portfolio.studyhelper.dto.response.StudyNoteResponse;
import com.portfolio.studyhelper.entity.StudyNote;
import com.portfolio.studyhelper.entity.Topic;
import com.portfolio.studyhelper.entity.User;
import com.portfolio.studyhelper.exception.ResourceNotFoundException;
import com.portfolio.studyhelper.repository.StudyNoteRepository;
import com.portfolio.studyhelper.repository.TopicRepository;
import com.portfolio.studyhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final StudyNoteRepository studyNoteRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public List<StudyNoteResponse> getUserNotes(Long userId, Long topicId, String search) {
        if (search != null && !search.isBlank()) {
            return studyNoteRepository.searchByUserId(userId, search).stream()
                    .map(StudyNoteResponse::from)
                    .toList();
        }
        if (topicId != null) {
            return studyNoteRepository.findByUserIdAndTopicId(userId, topicId).stream()
                    .map(StudyNoteResponse::from)
                    .toList();
        }
        return studyNoteRepository.findByUserIdWithTopics(userId).stream()
                .map(StudyNoteResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudyNoteResponse getNoteById(Long id, Long userId) {
        StudyNote note = studyNoteRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        return StudyNoteResponse.from(note);
    }

    @Transactional
    public StudyNoteResponse createNote(Long userId, CreateNoteRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Topic topic = null;
        if (request.topicId() != null) {
            topic = topicRepository.findById(request.topicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + request.topicId()));
        }

        StudyNote note = StudyNote.builder()
                .user(user)
                .topic(topic)
                .title(request.title())
                .content(request.content())
                .build();

        studyNoteRepository.save(note);
        return StudyNoteResponse.from(note);
    }

    @Transactional
    public StudyNoteResponse updateNote(Long id, Long userId, UpdateNoteRequest request) {
        StudyNote note = studyNoteRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));

        note.setTitle(request.title());
        note.setContent(request.content());

        studyNoteRepository.save(note);
        return StudyNoteResponse.from(note);
    }

    @Transactional
    public void deleteNote(Long id, Long userId) {
        StudyNote note = studyNoteRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        studyNoteRepository.delete(note);
    }
}
