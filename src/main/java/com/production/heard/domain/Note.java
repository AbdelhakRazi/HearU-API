package com.production.heard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Blob;

@Entity
@Table(name = "notes")
public class Note {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "audio_data")
    private byte[] audioData;
    @Column(name = "audio_length")
    private double audioLength;
    @Column(name ="saved")
    private boolean saved;

    public Note() {
    }

    public Note(String content, byte[] audioData, double audioLength, boolean saved) {
        this.content = content;
        this.audioData = audioData;
        this.audioLength = audioLength;
        this.saved = saved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getAudioData() {
        return audioData;
    }

    public void setAudioData(byte[] audioData) {
        this.audioData = audioData;
    }

    public double getAudioLength() {
        return audioLength;
    }

    public void setAudioLength(double audioLength) {
        this.audioLength = audioLength;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", audioData=" + audioData +
                ", audioLength=" + audioLength +
                ", saved=" + saved +
                '}';
    }
}
