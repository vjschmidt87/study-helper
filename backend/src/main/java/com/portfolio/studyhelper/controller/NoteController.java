package com.portfolio.studyhelper.controller;

import com.portfolio.studyhelper.dto.request.CreateNoteRequest;
import com.portfolio.studyhelper.dto.request.UpdateNoteRequest;
import com.portfolio.studyhelper.dto.response.StudyNoteResponse;
import com.portfolio.studyhelper.entity.User;
import com.portfolio.studyhelper.exception.ResourceNotFoundException;
import com.portfolio.studyhelper.repository.UserRepository;
import com.portfolio.studyhelper.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<StudyNoteResponse>> getUserNotes(
            @RequestParam(required = false) Long topicId,
            @RequestParam(required = false) String search) {
        Long userId = getDefaultUserId();
        return ResponseEntity.ok(noteService.getUserNotes(userId, topicId, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyNoteResponse> getNoteById(@PathVariable Long id) {
        Long userId = getDefaultUserId();
        return ResponseEntity.ok(noteService.getNoteById(id, userId));
    }

    @PostMapping
    public ResponseEntity<StudyNoteResponse> createNote(@Valid @RequestBody CreateNoteRequest request) {
        Long userId = getDefaultUserId();
        return ResponseEntity.ok(noteService.createNote(userId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyNoteResponse> updateNote(
            @PathVariable Long id,
            @Valid @RequestBody UpdateNoteRequest request) {
        Long userId = getDefaultUserId();
        return ResponseEntity.ok(noteService.updateNote(id, userId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        Long userId = getDefaultUserId();
        noteService.deleteNote(id, userId);
        return ResponseEntity.noContent().build();
    }

    private Long getDefaultUserId() {
        User user = userRepository.findByUsername("default")
                .orElseThrow(() -> new ResourceNotFoundException("Default user not found"));
        return user.getId();
    }
}
