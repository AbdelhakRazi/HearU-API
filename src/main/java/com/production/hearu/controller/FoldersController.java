package com.production.hearu.controller;

import com.production.hearu.domain.Folder;
import com.production.hearu.domain.Note;
import com.production.hearu.domain.User;
import com.production.hearu.exceptions.RessourceNotFoundException;
import com.production.hearu.repository.FolderRepository;
import com.production.hearu.repository.NotesRepository;
import com.production.hearu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/folders")
public class FoldersController {
    private final NotesRepository notesRepository;
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<List<Folder>> getFolders(Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<Folder> folderList = folderRepository.findByUsersId(user.getId());
        if (folderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(folderList, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder, Principal connectedUser) throws RessourceNotFoundException {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        User savedUser = userRepository
                .findById(user.getId())
                .orElseThrow(() -> new RessourceNotFoundException(
                        String.format("User with id %s is not found", user.getId())));
        folder.addUser(savedUser);
        Folder savedFolder = folderRepository.save(folder);
        return new ResponseEntity<>(savedFolder, HttpStatus.CREATED);

    }
    @PutMapping("/{folderId}")
    public ResponseEntity<Folder> updateFolder(@PathVariable int folderId, @RequestBody Folder folderRequest)
            throws RessourceNotFoundException {
        Folder folder = folderRepository
                .findById(folderId)
                .orElseThrow(() -> new RessourceNotFoundException(
                        String.format("Folder with id %s is not found", folderId)));
        folder.setName(folderRequest.getName());
        Folder savedFolder = folderRepository.save(folder);
        return new ResponseEntity<>(savedFolder, HttpStatus.OK);
    }
    @DeleteMapping("{folderId}")
    public ResponseEntity<HttpStatus> deleteFolder(@PathVariable int folderId) throws RessourceNotFoundException {
        Folder folder = folderRepository
                .findById(folderId)
                .orElseThrow(() -> new RessourceNotFoundException(
                        String.format("Folder with id %s is not found", folderId)));
        folderRepository.delete(folder);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{folderId}/notes")
    public ResponseEntity<List<Note>> getFolderNotes(@PathVariable int folderId, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<Note> noteList = notesRepository.findByUserIdAndFolderId(user.getId(), folderId);
        if (noteList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(noteList, HttpStatus.OK);
    }
    @PostMapping("/{folderId}/notes")
    public ResponseEntity<Note> createFolderNote(@PathVariable int folderId, @RequestBody Note note, Principal connectedUser)
            throws RessourceNotFoundException {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        User savedUser = userRepository
                .findById(user.getId())
                .orElseThrow(() -> new RessourceNotFoundException(
                        String.format("User with id %s is not found", user.getId())));
        Folder folder = folderRepository
                .findById(folderId)
                .orElseThrow(() -> new RessourceNotFoundException(
                        String.format("Folder with id %s is not found", folderId)));
        note.setUser(savedUser);
        note.setFolder(folder);
        Note savedNote = notesRepository.save(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }
}
