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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folders")
public class FoldersController {
    private final NotesRepository notesRepository;
    private final FolderRepository folderRepository;
    @GetMapping("")
    public List<Folder> getFolders(Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return folderRepository.findByUsersId(user.getId());
    }
    @GetMapping("/{folderId}/notes")
    public List<Note> getFolderNotes(@PathVariable int folderId, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return notesRepository.findByUserIdAndFolderId(user.getId(), folderId);
    }
    @PostMapping("")
    public Folder createFolder(@RequestBody Folder folder, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        // I think retrieve user entity and add the folder :P
        return folderRepository.saveByUsersId(folder, user.getId());
    }
    /*@PostMapping("/{folderId}/notes")
    public Note createFolderNote(@RequestBody Note note, @PathVariable int folderId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return notesRepository.saveByFolderId(note, folderId);
    }
    @PutMapping("")
    public Folder updateFolder(@RequestBody Folder folder){
        return folderRepository.save(folder);
    }
    @DeleteMapping("{folderId}")
    public void deleteFolder(@PathVariable int folderId, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Optional<Folder> folder =  folderRepository.findById(folderId);
        folder.ifPresent(folderRepository::delete);
    }

   */
}
