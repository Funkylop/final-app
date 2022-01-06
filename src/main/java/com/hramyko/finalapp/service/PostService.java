package com.hramyko.finalapp.service;

import com.hramyko.finalapp.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> findUserPosts(int idUser);
    List<Post> findAllPosts();
    void createPost(String jsonString);
    void deletePost(int id, int idUser);
    void deletePost(int id);
    void updatePost(int id, String jsonString, int idUser);
    void updatePost(int id, String jsonString);
}
