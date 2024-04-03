package com.production.hearu.controller;

import com.production.hearu.domain.Folder;
import com.production.hearu.domain.Note;
import com.production.hearu.domain.User;
import com.production.hearu.repository.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NotesController {
    private final NotesRepository notesRepository;
    @GetMapping("")
    public List<Note> getPlainUserNotes(Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return notesRepository.findByUserIdAndFolderIdIsNull(user.getId());
    }
    @PostMapping("")
    public Note createNote(@RequestBody Note note, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return notesRepository.save(note);
    }
    @PutMapping("")
    public Note updateNote(@RequestBody Note note){
        return notesRepository.save(note);
    }
    @DeleteMapping("{noteId}")
    public void deleteNote(@PathVariable int noteId){
        Optional<Note> note = notesRepository.findById(noteId);
        note.ifPresent(notesRepository::delete);
    }
}
