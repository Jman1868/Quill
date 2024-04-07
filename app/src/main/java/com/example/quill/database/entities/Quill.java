package com.example.quill.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.quill.database.QuillDatabase;

import java.util.Objects;

@Entity(tableName = QuillDatabase.QUILL_TABLE)
public class Quill {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String content;
    private String category;
    private int userId;

    public Quill(String title, String content, String category, int userId) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quill quill = (Quill) o;
        return id == quill.id && userId == quill.userId && Objects.equals(title, quill.title) && Objects.equals(content, quill.content) && Objects.equals(category, quill.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, category, userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}