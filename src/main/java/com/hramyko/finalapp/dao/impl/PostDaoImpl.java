package com.hramyko.finalapp.dao.impl;

import com.hramyko.finalapp.dao.PostDao;
import com.hramyko.finalapp.entity.Post;
import com.hramyko.finalapp.entity.Role;
import com.hramyko.finalapp.entity.Status;
import com.hramyko.finalapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class PostDaoImpl implements PostDao {

    private final RowMapper<Post> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Post(
            resultSet.getInt("id"), resultSet.getInt("id_game_object"),
            resultSet.getInt("price"));

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> findUserPosts(int idUser) {
        return jdbcTemplate.query("SELECT posts.id, posts.id_game_object, posts.price FROM posts " +
                                  "JOIN game_objects on posts.id_game_object = game_objects.id " +
                                  "and game_objects.id_user = ?", ROW_MAPPER, idUser);
    }

    @Override
    public void createPost(Post post) {
        jdbcTemplate.update("INSERT INTO posts(id_game_object, price) VALUES(?, ?)",
                            post.getIdGameObject(), post.getPrice());
    }

    @Override
    public void deletePost(int id) {
        jdbcTemplate.update("DELETE FROM posts WHERE id = ?", id);
    }

    @Override
    public Post findPostById(int idPost) {
        return jdbcTemplate.queryForObject("SELECT * FROM posts WHERE id = ?",
                ROW_MAPPER, idPost);
    }

    @Override
    public void updatePost(int id, Post post) {
        jdbcTemplate.update("UPDATE posts SET price = ? WHERE id = ?", post.getPrice(), id);
    }

    @Override
    public List<Post> findAll() {
        return jdbcTemplate.query("SELECT * FROM posts", ROW_MAPPER);
    }
}
