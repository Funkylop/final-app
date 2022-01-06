package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameObjectRepository extends JpaRepository<GameObject, Integer> {
    List<GameObject> findAllByUserId(int id);
    List<GameObject> findAllByGameIdIn(List<Integer> ids);
}
