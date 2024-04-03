package com.production.hearu.repository;

import com.production.hearu.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {
    List<Folder> findByUsersId(int userId);
}
