package com.hramyko.finalapp.entity;

import java.util.Objects;

public class WaitingList {
    int id;
    int idUser;

    public WaitingList() {
    }

    public WaitingList(int userId) {
        this.idUser = userId;
    }

    public WaitingList(int id, int userId) {
        this.id = id;
        this.idUser = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaitingList that = (WaitingList) o;
        return id == that.id && idUser == that.idUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser);
    }

    @Override
    public String toString() {
        return "WaitingList{" +
                "id=" + id +
                ", userId=" + idUser +
                '}';
    }
}
