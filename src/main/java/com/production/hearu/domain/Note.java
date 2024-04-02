package com.production.hearu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notes")
public class Note {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "audio_path")
    private String audioPath;
    @Column(name = "audio_length")
    private double audioLength;
    @Column(name ="saved")
    private boolean saved;
    @Column(name = "user_id")
    private int userId;
    @Column(name= "folder_id")
    private Integer folderId;
}
