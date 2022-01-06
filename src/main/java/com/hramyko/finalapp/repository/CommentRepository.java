package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByApprovedFalse();
    List<Comment> findCommentsByPostId(Integer postId);
    List<Comment> findCommentsByAuthorId(Integer userId);
}
