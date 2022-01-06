package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.entity.Comment;
import com.hramyko.finalapp.repository.CommentRepository;
import com.hramyko.finalapp.service.CommentService;
import com.hramyko.finalapp.service.parser.JsonParser;
import com.hramyko.finalapp.service.validator.CommentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentValidator commentValidator;

    @Autowired
    CommentServiceImpl(CommentRepository commentRepository, CommentValidator commentValidator) {
        this.commentRepository = commentRepository;
        this.commentValidator = commentValidator;
    }

    @Transactional
    @Override
    public List<Comment> findAllCommentsOfUser(int id) {
        return commentRepository.findCommentsByAuthorId(id);
    }

    @Transactional
    @Override
    public List<Comment> findAllCommentsOfPost(int idPost) {
        return commentRepository.findCommentsByPostId(idPost);
    }

    @Transactional
    @Override
    public Comment findComment(int id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            return optionalComment.get();
        } else throw new RuntimeException("Comment with such id doesn't exist");
    }

    @Transactional
    @Override
    public void saveComment(String jsonString) {
        Comment comment = (Comment) JsonParser.getObjectFromJson(jsonString, Comment.class.getName());
        if (comment != null) {
            commentValidator.validateMark(comment.getMark());
            commentValidator.validateMessage(comment.getMessage());
            commentRepository.save(comment);
        } else throw new RuntimeException("Error of saving comment");
    }

    @Transactional
    @Override
    public void updateComment(int id, String jsonString) {
        Comment newComment = (Comment) JsonParser.getObjectFromJson(jsonString, Comment.class.getName());
        if (newComment == null) throw new RuntimeException("Error of updating comment");
        Optional<Comment> optionalComment = commentRepository.findById(id);
        Comment comment;
        if (optionalComment.isPresent()) {
            comment = optionalComment.get();
        } else throw new RuntimeException("Comment with such id doesn't exist");
        if (newComment.getMessage() != null) {
            commentValidator.validateMessage(newComment.getMessage());
            comment.setMessage(newComment.getMessage());
        }
        if (newComment.getMark() != 0) {
            commentValidator.validateMark(newComment.getMark());
            comment.setMark(newComment.getMark());
        }
        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void approveComment(int id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            comment.get().setApproved(true);
            commentRepository.save(comment.get());
        } else throw new RuntimeException("Comment with such id doesn't exist");
    }

    @Transactional
    @Override
    public void destroyComment(int id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<Comment> findAllUnapprovedComments() {
        return commentRepository.findCommentsByApprovedFalse();
    }
}
