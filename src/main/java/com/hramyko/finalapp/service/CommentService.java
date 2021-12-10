package com.hramyko.finalapp.service;

import com.hramyko.finalapp.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAllCommentsOfUser(int id);
    List<Comment> findAllCommentsOfPost(int idPost);
    Comment findComment(int id);
    void saveComment(String jsonString);
    void updateComment(int id, String jsonString);
    void approveComment(int id);
    void destroyComment(int id);
    List<Comment> findAllUnapprovedComments();
}
