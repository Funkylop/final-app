package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {
    ConfirmationToken findConfirmationTokenByConfirmationToken(String token);
    void deleteConfirmationTokenByEmailAndTokenType(String email, String tokeType);
}
