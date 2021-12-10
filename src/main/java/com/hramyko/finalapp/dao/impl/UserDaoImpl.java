package com.hramyko.finalapp.dao.impl;

import com.hramyko.finalapp.dao.UserDao;
import com.hramyko.finalapp.entity.Role;
import com.hramyko.finalapp.entity.Status;
import com.hramyko.finalapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.*;

@Component
public class UserDaoImpl implements UserDao {

    private final RowMapper<User> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new User(
            resultSet.getInt("id"), resultSet.getString("email"),
            resultSet.getString("password"), resultSet.getString("first_name"),
            resultSet.getString("last_name"), resultSet.getDate("created_at"),
            Role.valueOf(resultSet.getString("role_")), Status.valueOf(resultSet.getString("status")));

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", ROW_MAPPER);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?", ROW_MAPPER, email);
        return user;
    }

    @Override
    public Map<User, Double> getTopTraders() {
        Map<User, Double> traderRating = new LinkedHashMap<>();
        List<User> users = findAllTraders();
        for (User user: users) {
            Double rating = jdbcTemplate.queryForObject("SELECT AVG(comments.mark) as rating FROM comments \n" +
                    "JOIN posts on comments.id_post = posts.id \n" +
                    "Join game_objects on posts.id_game_object = game_objects.id \n" +
                    "Join users on users.id = game_objects.id_user \n" +
                    "Where users.role_ = 'TRADER' and users.id = ? and comments.approved = 1\n" +
                    "group by users.email;", Double.class, user.getId());
            traderRating.put(user, rating);
        }
        return traderRating;
    }

    @Override
    public void updateUserStatus(int id, String status) {
        jdbcTemplate.update("UPDATE users SET status = ? where id = ?", status, id);
    }

    @Override
    public User findUserById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",  ROW_MAPPER, id);
    }

    @Override
    public User updateUser(int id, User user) {
        jdbcTemplate.update("UPDATE users SET password = ? first_name = ? last_name = ? where id = ?",
                getEncodedPassword(user.getPassword()), user.getFirstName(), user.getLastName(), id);
        return findUserById(id);
    }

    @Override
    public User saveUser(User user) {
        jdbcTemplate.update("INSERT INTO users(email, password, first_name, last_name) VALUES(?, ?, ?, ?)",
                user.getEmail(), getEncodedPassword(user.getPassword()),
                user.getFirstName(), user.getLastName());
        return findUserByEmail(user.getEmail());
    }

    @Override
    public void destroyUser(int id) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
    }

    @Override
    public void updateUserRole(int id, String role) {
        jdbcTemplate.update("UPDATE users SET role_ = ? where id = ?", role, id);
    }

    @Override
    public List<User> findAllTraders() {
        return jdbcTemplate.query("SELECT * FROM users WHERE role_ = 'TRADER'", ROW_MAPPER);
    }

    private String getEncodedPassword(String password)
    {
        return new BCryptPasswordEncoder().encode(password);
    }
}
