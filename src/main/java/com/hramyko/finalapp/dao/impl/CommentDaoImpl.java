package com.hramyko.finalapp.dao.impl;

import com.hramyko.finalapp.dao.CommentDao;
import com.hramyko.finalapp.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class CommentDaoImpl implements CommentDao {

    private final RowMapper<Comment> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Comment(
            resultSet.getInt("id"), resultSet.getString("message"),
            resultSet.getBoolean("approved"), resultSet.getInt("id_author"),
            resultSet.getInt("id_post"), resultSet.getInt("mark"),
            resultSet.getDate("created_at"));

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> findAllUserComments(int idUser) {
        return jdbcTemplate.query("SELECT * FROM comments JOIN posts on comments.id_post = posts.id " +
                        "JOIN game_objects on game_objects.id = posts.id_game_object  " +
                        " WHERE game_objects.id_user = ? AND approved = 1",
                ROW_MAPPER, idUser);
    }

    @Override
    public List<Comment> findAllCommentsOfPost(int idPost) {
        return jdbcTemplate.query("SELECT * FROM comments WHERE id_post = ?  AND approved = 1",
                ROW_MAPPER, idPost);
    }

    @Override
    public Comment findComment(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM comments WHERE id = ?",
                ROW_MAPPER, id);
    }

    @Override
    public void saveComment(Comment comment) {
        jdbcTemplate.update("INSERT INTO comments(message, id_author, id_post, mark) VALUES(?, ?, ?, ?)", comment.getMessage(),
                comment.getIdAuthor(), comment.getIdPost(), comment.getMark());
    }

    @Override
    public void updateComment(int id, Comment comment) {
        jdbcTemplate.update("UPDATE comments SET approved = ?, message = ? WHERE id = ?", comment.isApproved(), comment.getMessage(), id);
    }

    @Override
    public void updateCommentStatus(int id, Comment comment) {
        jdbcTemplate.update("UPDATE comments SET approved = ? WHERE id = ?", comment.isApproved(), id);
    }

    @Override
    public void deleteComment(int idUser, int id) {
            jdbcTemplate.update("DELETE FROM comments WHERE id = ?", id);
    }

    @Override
    public List<Comment> findAllUnapprovedComments() {
        return jdbcTemplate.query("SELECT * FROM comments WHERE approved = 0", ROW_MAPPER);
    }
}
