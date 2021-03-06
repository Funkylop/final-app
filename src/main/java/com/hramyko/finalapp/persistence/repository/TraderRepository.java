package com.hramyko.finalapp.persistence.repository;

import com.hramyko.finalapp.persistence.entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Integer> {
}
