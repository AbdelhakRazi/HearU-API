package com.production.hearu.controller;

import com.production.hearu.domain.Note;
import com.production.hearu.repository.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NotesController {
    private final NotesRepository notesRepository;
    @GetMapping("")
    public List<Note> fetchPlainUserNotes(Authentication authentication){
        return null;
    }
}
