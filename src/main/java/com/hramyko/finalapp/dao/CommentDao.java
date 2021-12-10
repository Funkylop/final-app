package com.hramyko.finalapp.dao;

import com.hramyko.finalapp.entity.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> findAllUserComments(int id);
    List<Comment> findAllCommentsOfPost(int idPost);
    Comment findComment(int id);
    void saveComment(Comment comment);
    void updateComment(int id, Comment comment);
    void updateCommentStatus(int id, Comment comment);
    void deleteComment(int idUser, int id);
    List<Comment> findAllUnapprovedComments();
}
