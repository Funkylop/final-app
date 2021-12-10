package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.dao.CommentDao;
import com.hramyko.finalapp.entity.Comment;
import com.hramyko.finalapp.service.CommentService;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.validator.CommentValidator;
import com.hramyko.finalapp.service.parser.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final CommentValidator commentValidator;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, CommentValidator commentValidator,
                              UserService userService) {
        this.commentDao = commentDao;
        this.commentValidator = commentValidator;
        this.userService = userService;
    }

    @Override
    public List<Comment> findAllCommentsOfUser(int id) {
        commentValidator.validateId(id);
        return commentDao.findAllUserComments(id);
    }

    @Override
    public List<Comment> findAllCommentsOfPost(int idComment) {
        return commentDao.findAllCommentsOfPost(idComment);
    }

    @Override
    public Comment findComment(int id) {
        commentValidator.validateId(id);
        return commentDao.findComment(id);
    }

    @Override
    public void saveComment(String jsonString) {
        Comment comment = (Comment) JsonParser.getObjectFromJson(jsonString, Comment.class.getName());
        if (comment == null) {
            throw new RuntimeException("Error of creating Comment");
        } else {
            commentValidator.validateMessage(comment.getMessage());
            commentValidator.validateId(comment.getIdPost());
            commentValidator.validateMark(comment.getMark());
            comment.setIdAuthor(userService.currentUser().getId());
            commentDao.saveComment(comment);
        }
    }

    @Override
    public void updateComment(int id, String jsonString) {
        Comment comment = (Comment) JsonParser.getObjectFromJson(jsonString, Comment.class.getName());
        if (comment == null) {
            throw new RuntimeException("Error of creating Comment");
        } else {
            comment.setApproved(false);
            commentValidator.validateId(id);
            if (userHasComment(userService.currentUser().getId(), id)) {
                commentValidator.validateMessage(comment.getMessage());
                commentValidator.validateMark(comment.getMark());
                commentDao.updateComment(id, comment);
            } else {
                throw new RuntimeException("You dont have comment with such id");
            }
        }
    }

    @Override
    public void approveComment(int id) {
        commentValidator.validateId(id);
        Comment comment = commentDao.findComment(id);
        comment.setApproved(true);
        commentDao.updateCommentStatus(comment.getId(), comment);
    }

    @Override
    public void destroyComment(int id) {
        int idUser = userService.currentUser().getId();
        commentValidator.validateId(id);
        if (userHasComment(idUser, id)) {
            commentDao.deleteComment(idUser, id);
        } else {
            throw new RuntimeException("You dont have comment with such id");
        }
    }

    @Override
    public List<Comment> findAllUnapprovedComments() {
        return commentDao.findAllUnapprovedComments();
    }

    private boolean userHasComment(int idUser, int id) {
        return idUser == commentDao.findComment(id).getIdAuthor();
    }
}
