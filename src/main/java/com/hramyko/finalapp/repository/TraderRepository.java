package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Integer> {
    Trader findByEmail(String email);
}
