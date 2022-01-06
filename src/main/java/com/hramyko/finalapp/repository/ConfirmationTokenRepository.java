package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.ConfirmationToken;
import com.hramyko.finalapp.entity.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {
    ConfirmationToken findConfirmationTokenByConfirmationToken(String token);
    void deleteConfirmationTokenByEmailAndTokenType(String email, TokenType tokenType);
}
