package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, Integer> {
    List<GameObject> findAllByTrader(Integer id);
    List<GameObject> findAllByGameIdIn(List<Integer> ids);
}
