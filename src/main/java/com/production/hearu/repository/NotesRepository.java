package com.production.hearu.repository;

import com.production.hearu.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Note, Integer> {
   List<Note> findByUserIdAndFolderIdIsNull(int userId);
   //Note saveByFolderId(Note note, int folderId);
   List<Note> findByUserIdAndFolderId(int userId, int folderId);
}
