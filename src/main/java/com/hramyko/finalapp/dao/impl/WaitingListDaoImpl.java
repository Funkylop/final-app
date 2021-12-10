package com.hramyko.finalapp.dao.impl;

import com.hramyko.finalapp.dao.WaitingListDao;
import com.hramyko.finalapp.entity.WaitingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class WaitingListDaoImpl implements WaitingListDao {

    private final RowMapper<WaitingList> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new WaitingList(
            resultSet.getInt("id"), resultSet.getInt("id_user"));

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WaitingListDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getUserId(int formId) {
        return jdbcTemplate.queryForObject("SELECT id_user FROM waiting_list where id = ?", Integer.class, formId);
    }

    @Override
    public void deleteUserForm(int formId) {
        jdbcTemplate.update("DELETE FROM waiting_list WHERE id = ?", formId);
    }

    @Override
    public void addUserForm(int userId) {
        jdbcTemplate.update("INSERT INTO waiting_list(id_user) VALUES(?)", userId);
    }

    @Override
    public List<WaitingList> findAll() {
        return jdbcTemplate.query("SELECT * FROM waiting_list", ROW_MAPPER);
    }
}
