package com.hramyko.finalapp.entity;

import java.util.List;
import java.util.Objects;

public class Post {

    int id;
    int idGameObject;
    double price;
    List<Comment> comments;

    public Post() {
    }

    public Post(int idGameObject, double price) {
        this.idGameObject = idGameObject;
        this.price = price;
    }

    public Post(int id, int idGameObject, double price) {
        this.id = id;
        this.idGameObject = idGameObject;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGameObject() {
        return idGameObject;
    }

    public void setIdGameObject(int idGameObject) {
        this.idGameObject = idGameObject;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && idGameObject == post.idGameObject && Double.compare(post.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idGameObject, price);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", idGameObject=" + idGameObject +
                ", price=" + price +
                ", comments=" + comments +
                '}';
    }
}
