package com.hramyko.finalapp.entity;

import java.util.Date;
import java.util.Objects;

public class GameObject {

    private int id;
    private int idUser;
    private int idGame;
    private String title;
    private String text;
    private GameObjectStatus status;
    private Date createdAt;
    private Date updatedAt;

    public GameObject(int idGame, String title, String text) {
        this.idGame = idGame;
        this.title = title;
        this.text = text;
    }

    public GameObject(String title, String text, GameObjectStatus status) {
        this.title = title;
        this.text = text;
        this.status = status;
    }

    public GameObject() {
    }

    public GameObject(int id, int idUser, int idGame, String title,
                      String text, GameObjectStatus status, Date createdAt,
                      Date updatedAt) {
        this.id = id;
        this.idUser = idUser;
        this.idGame = idGame;
        this.title = title;
        this.text = text;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public GameObjectStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = GameObjectStatus.valueOf(status.toUpperCase());
    }

    public void setStatus(GameObjectStatus status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return id == that.id && idUser == that.idUser && idGame == that.idGame && Objects.equals(title, that.title) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, idGame, title, text);
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "idGameObject=" + id +
                ", status = " + status +
                ", idUser=" + idUser +
                ", idGame=" + idGame +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
