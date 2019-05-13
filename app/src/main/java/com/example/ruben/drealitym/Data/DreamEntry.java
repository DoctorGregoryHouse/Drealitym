package com.example.ruben.drealitym.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dream_table") //this annotation creates a  table
public class DreamEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int type;
    private int  checkFile;
    private String date;
    private String title;
    private String text;
    private int favourite;
    private String audioPath;


    public DreamEntry(int type, int checkFile, String date, String title, String text, int favourite, String audioPath) {
        this.type = type;
        this.checkFile = checkFile;
        this.date = date;
        this.title = title;
        this.text = text;
        this.favourite = favourite;
        this.audioPath = audioPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getCheckFile() {
        return checkFile;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getFavourite() {
        return favourite;
    }

    public String getAudioPath() {
        return audioPath;
    }
}
