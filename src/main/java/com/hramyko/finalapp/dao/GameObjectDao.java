package com.hramyko.finalapp.dao;

import com.hramyko.finalapp.entity.GameObject;

import java.util.Date;
import java.util.List;

public interface GameObjectDao {
    List<GameObject> findAllGameObjects();
    List<GameObject> findAllGameObjectOfGame(int idGame);
    void saveGameObject(GameObject gameObject);
    void updateGameObject(int id, int idUser,GameObject gameObject);
    void destroyGameObject(int id, int idUser);
    List<GameObject> findAllUserGameObjects(int idUser);
    void updateGameObjectStatus(int id, int idUser, String status, Date date);
    GameObject findGameObject(int id, int idUser);
}
