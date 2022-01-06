package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.entity.GameObject;
import com.hramyko.finalapp.repository.GameObjectRepository;
import com.hramyko.finalapp.service.GameObjectService;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.parser.JsonParser;
import com.hramyko.finalapp.service.validator.GameObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GameObjectServiceImpl implements GameObjectService {

    private final GameObjectRepository gameObjectRepository;
    private final GameObjectValidator gameObjectValidator;
    private final UserService userService;

    @Autowired
    GameObjectServiceImpl(GameObjectRepository gameObjectRepository, GameObjectValidator gameObjectValidator,
                          UserService userService) {
        this.gameObjectRepository = gameObjectRepository;
        this.gameObjectValidator = gameObjectValidator;
        this.userService = userService;
    }

    @Transactional
    @Override
    public List<GameObject> findAllGameObjects() {
        return gameObjectRepository.findAll();
    }

    @Transactional
    @Override
    public List<GameObject> findAllUserGameObjects(int id) {
        return gameObjectRepository.findAllByTrader(id);
    }

    @Transactional
    @Override
    public List<GameObject> findAllGameObjectsOfGame(Integer[] gameIds) {
        List<Integer> ids = new ArrayList<>();
        Collections.addAll(ids, gameIds);
        return gameObjectRepository.findAllByGameIdIn(ids);
    }

    @Transactional
    @Override
    public void saveGameObject(String jsonString) {
        GameObject gameObject = (GameObject) JsonParser.getObjectFromJson(jsonString, GameObject.class.getName());
        if (gameObject != null) {
            gameObjectRepository.save(gameObject);
        } else throw new RuntimeException("Error of saving game object");
    }

    @Transactional
    @Override
    public void updateGameObject(int id, String jsonString) {
        GameObject gameObject = getGameObject(id);
        if (isOwner(userService.currentUser().getId(), gameObject)) {
            GameObject newGameObject = (GameObject) JsonParser.getObjectFromJson(jsonString, GameObject.class.getName());
            if (newGameObject == null) throw new RuntimeException("Error of updating game object");
            if (newGameObject.getText() != null) {
                gameObjectValidator.validateTextField(newGameObject.getText());
                gameObject.setText(newGameObject.getText());
            }
            gameObjectRepository.save(gameObject);
        }
    }

    @Transactional
    @Override
    public void updateGameObjectStatus(int id, String status) {
        GameObject gameObject = getGameObject(id);
        gameObject.setStatus(status);
        gameObjectRepository.save(gameObject);
    }

    @Transactional
    @Override
    public void destroyGameObject(int id, int idUser) {
        GameObject gameObject = getGameObject(id);
        if (isOwner(userService.currentUser().getId(), gameObject)) {
            gameObjectRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public boolean isOwner(int idUser, GameObject gameObject) {
        if (gameObject.getUser().getId() != idUser) {
            throw new RuntimeException("You aren't owner of this object!");
        } else return true;
    }

    private GameObject getGameObject(int id) {
        Optional<GameObject> optionalGameObject = gameObjectRepository.findById(id);
        if (optionalGameObject.isPresent()) {
            return optionalGameObject.get();
        } else throw new RuntimeException("Object with such id doesn't exists");
    }
}