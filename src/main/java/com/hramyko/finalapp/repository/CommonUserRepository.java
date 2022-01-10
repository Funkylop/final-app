package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonUserRepository extends JpaRepository<CommonUser, Integer> {
}
