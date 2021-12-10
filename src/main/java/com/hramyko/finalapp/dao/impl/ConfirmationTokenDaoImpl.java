package com.hramyko.finalapp.dao.impl;

import com.hramyko.finalapp.dao.ConfirmationTokenDao;
import com.hramyko.finalapp.entity.ConfirmationToken;
import com.hramyko.finalapp.entity.TokenType;
import com.hramyko.finalapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@Component
public class ConfirmationTokenDaoImpl implements ConfirmationTokenDao {

    private final RowMapper<ConfirmationToken> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new ConfirmationToken(
            resultSet.getString("confirmation_token"), resultSet.getString("email"),
            resultSet.getDate("created_at"), TokenType.valueOf(resultSet.getString("token_type")));

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ConfirmationTokenDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        return jdbcTemplate.queryForObject("SELECT * FROM tokens WHERE confirmation_token = ?",
                ROW_MAPPER, confirmationToken);
    }

    @Override
    public void deleteConfirmationToken(User user, TokenType type) {
        jdbcTemplate.update("DELETE FROM tokens WHERE email = ? and token_type = ?", user.getEmail(), type.toString());
    }

    @Override
    public void createConfirmationToken(ConfirmationToken confirmationToken) {
        jdbcTemplate.update("INSERT INTO tokens(confirmation_token, email, token_type) VALUE(?, ?, ?)",
                confirmationToken.getConfirmationToken(), confirmationToken.getEmail(),
                confirmationToken.getTokenType().toString());
    }
}
