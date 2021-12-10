package com.hramyko.finalapp.dao;

import com.hramyko.finalapp.entity.Post;

import java.util.List;

public interface PostDao {
    List<Post> findUserPosts(int idUser);
    void createPost(Post post);
    void deletePost(int id);
    Post findPostById(int idPost);
    void updatePost(int id, Post post);
    List<Post> findAll();
}
