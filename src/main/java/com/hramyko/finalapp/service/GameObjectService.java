package com.hramyko.finalapp.service;


import com.hramyko.finalapp.entity.GameObject;

import java.util.List;

public interface GameObjectService {
    List<GameObject> findAllGameObjects();
    List<GameObject> findAllUserGameObjects();
    List<GameObject> findAllGameObjectsOfGame(int[] gameIds);
    void saveGameObject(String jsonString);
    void updateGameObject(int id, String string);
    void updateGameObjectStatus(int id,String status);
    void destroyGameObject(int id, int idUser);
    boolean userHasGameObj(int idGameObject, int idUser);
}
