package com.hramyko.finalapp.dao.impl;

import com.hramyko.finalapp.dao.GameDao;
import com.hramyko.finalapp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class GameDaoImpl implements GameDao {

    private final RowMapper<Game> RAW_MAPPER =  (ResultSet resultSet, int rowNum) -> new Game(
            resultSet.getInt("id"), resultSet.getString("title"));
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Game> findAllGames() {
        return jdbcTemplate.query("SELECT * FROM games", RAW_MAPPER);
    }

    @Override
    public Game findGameById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM games WHERE id = ?", RAW_MAPPER, id);
    }

    @Override
    public void saveGame(Game game) {
        jdbcTemplate.update("INSERT INTO games(title) VALUES (?)", game.getTitle());
    }

    @Override
    public void updateGame(int id, Game game) {
        jdbcTemplate.update("UPDATE games SET title = ? WHERE id = ?", game.getTitle(), id);
    }

    @Override
    public void destroyGame(int id) {
        jdbcTemplate.update("DELETE FROM games WHERE id = ?", id);
    }
}
