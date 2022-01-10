package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.Comment;
import com.hramyko.finalapp.entity.Post;
import com.hramyko.finalapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByApprovedFalse();
    List<Comment> findCommentsByPostAndApprovedTrue(Post post);
    List<Comment> findCommentsByAuthorAndApprovedTrue(User user);
}
