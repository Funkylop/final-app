package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByApprovedFalse();
    List<Comment> findCommentsByPostId(int postId);
    List<Comment> findCommentsByAuthorId(int userId);
}
