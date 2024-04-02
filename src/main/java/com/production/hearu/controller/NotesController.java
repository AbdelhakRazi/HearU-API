package com.production.hearu.controller;

import com.production.hearu.domain.Note;
import com.production.hearu.domain.User;
import com.production.hearu.repository.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

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

}
