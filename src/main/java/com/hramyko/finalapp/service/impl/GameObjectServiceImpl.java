package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.dao.GameObjectDao;
import com.hramyko.finalapp.entity.GameObject;
import com.hramyko.finalapp.service.GameObjectService;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.validator.GameObjectValidator;
import com.hramyko.finalapp.service.validator.UserValidator;
import com.hramyko.finalapp.service.parser.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GameObjectServiceImpl implements GameObjectService {

    private final GameObjectDao gameObjectDao;
    private final UserValidator userValidator;
    private final GameObjectValidator gameObjectValidator;
    private final UserService userService;

    @Autowired
    public GameObjectServiceImpl(GameObjectDao gameObjectDao, UserValidator userValidator,
                                 GameObjectValidator gameObjectValidator, UserService userService) {
        this.gameObjectDao = gameObjectDao;
        this.userValidator = userValidator;
        this.gameObjectValidator = gameObjectValidator;
        this.userService = userService;
    }

    @Override
    public List<GameObject> findAllGameObjects() {
        return gameObjectDao.findAllGameObjects();
    }

    @Override
    public List<GameObject> findAllUserGameObjects() {
        int idUser = userService.currentUser().getId();
        userValidator.validateId(idUser);
        return gameObjectDao.findAllUserGameObjects(idUser);
    }

    @Override
    public List<GameObject> findAllGameObjectsOfGame(int[] gameIds) {
        if (gameIds == null) {
            return findAllGameObjects();
        } else {
            List<GameObject> gameObjects = new ArrayList<>();
            for (int gameId : gameIds) {
                gameObjects.addAll(gameObjectDao.findAllGameObjectOfGame(gameId));
            }
            return gameObjects;
        }
    }

    @Override
    public void saveGameObject(String jsonString) {
        GameObject gameObject = (GameObject) JsonParser.getObjectFromJson(jsonString, GameObject.class.getName());
        if (gameObject == null) {
            throw new RuntimeException("Error of creating game");
        } else {
            gameObjectValidator.validateTextField(gameObject.getText());
            gameObjectValidator.validateTextField(gameObject.getTitle());
            gameObject.setIdUser(userService.currentUser().getId());
            gameObjectDao.saveGameObject(gameObject);
        }
    }

    @Override
    public void updateGameObject(int id, String jsonString) {
        int idUser = userService.currentUser().getId();
        GameObject gameObject = (GameObject) JsonParser.getObjectFromJson(jsonString, GameObject.class.getName());
        if (gameObject == null) {
            throw new RuntimeException("Error of updating game");
        } else {
            if (userHasGameObj(id, idUser)) {
                gameObjectValidator.validateId(id);
                gameObjectValidator.validateTextField(gameObject.getTitle());
                gameObjectValidator.validateTextField(gameObject.getText());
                gameObject.setUpdatedAt();
                gameObjectDao.updateGameObject(id, idUser, gameObject);
            }
        }
    }

    @Override
    public void updateGameObjectStatus(int id, String status) {
        int idUser = userService.currentUser().getId();
        if (userHasGameObj(id, idUser)) {
            gameObjectValidator.validateStatus(status);
            gameObjectDao.updateGameObjectStatus(id, idUser, status, new Date());
        }
    }

    @Override
    public void destroyGameObject(int id, int idUser) {
        if (userHasGameObj(id, idUser)) {
            gameObjectDao.destroyGameObject(id, idUser);
        }
    }

    public boolean userHasGameObj(int idGameObj, int idUser) {
        return gameObjectDao.findGameObject(idGameObj, idUser) != null;
    }
}
