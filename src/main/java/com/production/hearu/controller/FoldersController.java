package com.production.hearu.controller;

import com.production.hearu.domain.Folder;
import com.production.hearu.domain.Note;
import com.production.hearu.domain.User;
import com.production.hearu.repository.FolderRepository;
import com.production.hearu.repository.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folders")
public class FoldersController {
    private final NotesRepository notesRepository;
    private final FolderRepository folderRepository;
    @GetMapping("")
    public List<Folder> getFolders(Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return folderRepository.findByUserId(user.getId());
    }
    @PostMapping("")
    public List<Folder> createFolder(@PathVariable int folderId, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return folderRepository.findByUserId(user.getId());
    }
    @PutMapping("{folderId}")
    public List<Folder> updateFolder(@PathVariable int folderId, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return folderRepository.findByUserId(user.getId());
    }
    @DeleteMapping("{folderId}")
    public List<Folder> deleteFolder(@PathVariable int folderId, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return folderRepository.findByUserId(user.getId());
    }
    @GetMapping("/{folderId}/notes")
    public List<Note> getFolderUserNotes(@PathVariable int folderId, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return notesRepository.findByUserIdAndFolderId(user.getId(), folderId);
    }
}
