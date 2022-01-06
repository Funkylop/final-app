package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingListRepository extends JpaRepository<WaitingList, Integer> {
}
