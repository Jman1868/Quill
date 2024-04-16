package com.example.quill.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.quill.database.QuillDatabase;

import java.util.Objects;

@Entity(tableName = QuillDatabase.LIKED_TABLE)
public class Liked {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String content;
    private String category;
    private int userId;

    public Liked(String title, String content, String category, int userId) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Liked liked = (Liked) o;
        return id == liked.id && userId == liked.userId && Objects.equals(title, liked.title) && Objects.equals(content, liked.content) && Objects.equals(category, liked.category);
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
