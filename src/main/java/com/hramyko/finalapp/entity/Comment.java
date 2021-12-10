package com.hramyko.finalapp.entity;

import java.util.Date;
import java.util.Objects;

public class Comment {

    private int id;
    private String message;
    private boolean approved;
    private int idAuthor;
    private int idPost;
    private int mark;

    private Date createdAt;

    public Comment() {
    }

    public Comment(String message, boolean approved, int idAuthor, int idPost) {
        this.message = message;
        this.idAuthor = idAuthor;
        this.idPost = idPost;
    }

    public Comment(int id, String message, boolean approved, int idAuthor, int idPost, int mark, Date createdAt) {
        this.id = id;
        this.message = message;
        this.approved = approved;
        this.idAuthor = idAuthor;
        this.idPost = idPost;
        this.mark = mark;
        this.createdAt = createdAt;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id && approved == comment.approved && idAuthor == comment.idAuthor && idPost == comment.idPost && mark == comment.mark && message.equals(comment.message) && createdAt.equals(comment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, approved, idAuthor, idPost, mark, createdAt);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", approved=" + approved +
                ", idAuthor=" + idAuthor +
                ", idPost=" + idPost +
                ", mark=" + mark +
                ", createdAt=" + createdAt +
                '}';
    }
}
