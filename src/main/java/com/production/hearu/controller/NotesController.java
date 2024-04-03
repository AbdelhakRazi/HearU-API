package com.production.hearu.controller;

import com.production.hearu.domain.Folder;
import com.production.hearu.domain.Note;
import com.production.hearu.domain.User;
import com.production.hearu.exceptions.RessourceNotFoundException;
import com.production.hearu.repository.NotesRepository;
import com.production.hearu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NotesController {
    private final NotesRepository notesRepository;
    private final UserRepository userRepository;
    @GetMapping("")
    public ResponseEntity<List<Note>> getNonFolderNotes(Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<Note> notes = notesRepository.findByUserIdAndFolderIdIsNull(user.getId());
        if (notes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<Note> createNote(@RequestBody Note note, Principal connectedUser) throws RessourceNotFoundException {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        User savedUser = userRepository
                .findById(user.getId())
                .orElseThrow(() -> new RessourceNotFoundException(
                        String.format("User with id %s is not found", user.getId())));
        note.setUser(savedUser);
        Note savedNote = notesRepository.save(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }
    @PutMapping("/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable int noteId, @RequestBody Note noteRequest)
            throws RessourceNotFoundException {
       Note daoNote = notesRepository
               .findById(noteId)
               .orElseThrow(() -> new RessourceNotFoundException(
                       String.format("Note with id %s is not found", noteId)));
       daoNote.setContent(noteRequest.getContent());
       daoNote.setTitle(noteRequest.getTitle());
       daoNote.setAudioPath(noteRequest.getAudioPath());
       daoNote.setAudioLength(noteRequest.getAudioLength());
       Note savedNote = notesRepository.save(daoNote);
       return new ResponseEntity<>(savedNote, HttpStatus.OK);
        }
    @DeleteMapping("{noteId}")
    public ResponseEntity<HttpStatus> deleteNote(@PathVariable int noteId) throws RessourceNotFoundException {
        Note note = notesRepository
                .findById(noteId)
                .orElseThrow(() -> new RessourceNotFoundException(
                        String.format("Note with id %s is not found", noteId)));
        notesRepository.delete(note);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
