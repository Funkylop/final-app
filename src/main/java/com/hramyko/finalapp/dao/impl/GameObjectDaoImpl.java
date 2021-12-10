package com.hramyko.finalapp.dao.impl;

import com.hramyko.finalapp.dao.GameObjectDao;
import com.hramyko.finalapp.entity.GameObject;
import com.hramyko.finalapp.entity.GameObjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

@Component
public class GameObjectDaoImpl implements GameObjectDao {

    private final RowMapper<GameObject> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new GameObject(
            resultSet.getInt("id"), resultSet.getInt("id_user"),
            resultSet.getInt("id_game"), resultSet.getString("title"),
            resultSet.getString("text"), GameObjectStatus.valueOf(resultSet.getString("status")),
            resultSet.getDate("created_at"), resultSet.getDate("updated_at"));

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameObjectDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<GameObject> findAllGameObjects() {
        return jdbcTemplate.query("SELECT * FROM game_objects", ROW_MAPPER);
    }

    @Override
    public List<GameObject> findAllUserGameObjects(int idUser) {
        return jdbcTemplate.query("SELECT * FROM game_objects WHERE id_user = ?", ROW_MAPPER,
                                  idUser);
    }

    @Override
    public void updateGameObjectStatus(int id, int idUser, String status, Date date) {
        jdbcTemplate.update("UPDATE game_objects SET status = ?, update_at =  ? WHERE id = ? and id_user = ?",
                status, date, id, idUser);
    }

    @Override
    public GameObject findGameObject(int id, int idUser) {
        return jdbcTemplate.queryForObject("SELECT * FROM game_objects WHERE id = ? and id_user = ?",
                ROW_MAPPER, id, idUser);
    }

    @Override
    public List<GameObject> findAllGameObjectOfGame(int idGame) {
        return jdbcTemplate.query("SELECT * FROM game_objects WHERE id_game = ?", ROW_MAPPER, idGame);
    }

    @Override
    public void saveGameObject(GameObject gameObject) {
        jdbcTemplate.update("INSERT INTO game_objects(id_user, id_game, title, text) VALUES(?, ?, ?, ?)", gameObject.getIdUser(),
                            gameObject.getIdGame(), gameObject.getTitle(), gameObject.getText());
    }

    @Override
    public void updateGameObject(int id, int idUser, GameObject gameObject) {
        jdbcTemplate.update("UPDATE game_objects SET title = ?, status = ?, text = ?, updated_at = ? WHERE id = ? and id_user = ?",
                gameObject.getTitle(), gameObject.getStatus().toString(), gameObject.getText(), gameObject.getUpdatedAt(), id, idUser);
    }

    @Override
    public void destroyGameObject(int id, int idUser) {
        jdbcTemplate.update("DELETE FROM game_objects WHERE id = ? and id_user = ?", id, idUser);
    }
}
