package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.dao.GameDao;
import com.hramyko.finalapp.entity.Game;
import com.hramyko.finalapp.service.GameObjectService;
import com.hramyko.finalapp.service.GameService;
import com.hramyko.finalapp.service.validator.GameValidator;
import com.hramyko.finalapp.service.parser.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameDao gameDao;
    private final GameObjectService gameObjectService;
    private final GameValidator gameValidator;

    @Autowired
    public GameServiceImpl(GameDao gameDao, GameObjectService gameObjectService,
                           GameValidator gameValidator) {
        this.gameDao = gameDao;
        this.gameObjectService = gameObjectService;
        this.gameValidator = gameValidator;
    }

    @Override
    public List<Game> findGames() {
        List<Game> games = gameDao.findAllGames();
        for (Game game : games) {
            int[] gameId = new int[1];
            gameId[0] = game.getId();
            game.setGameObjects(gameObjectService.findAllGameObjectsOfGame(gameId));
        }
        return games;
    }

    @Override
    public Game findGameById(int id) {
        gameValidator.validateId(id);
        Game game = gameDao.findGameById(id);
        int[] gameId = new int[1];
        gameId[0] = game.getId();
        game.setGameObjects(gameObjectService.findAllGameObjectsOfGame(gameId));
        return game;
    }

    @Override
    public void saveGame(String jsonString) {
        Game game = (Game) JsonParser.getObjectFromJson(jsonString, Game.class.getName());
        if (game == null) {
            throw new RuntimeException("Error of creating game");
        } else {
            gameValidator.validateTitle(game.getTitle());
            gameDao.saveGame(game);
        }
    }

    @Override
    public void updateGame(int id, String jsonString) {
        Game game = (Game) JsonParser.getObjectFromJson(jsonString, Game.class.getName());
        gameValidator.validateId(id);
        if (game == null) {
            throw new RuntimeException("Error of updating game");
        } else {
            gameValidator.validateTitle(game.getTitle());
            gameDao.updateGame(id, game);
        }
    }

    @Override
    public void destroyGame(int id) {
        gameValidator.validateId(id);
        gameDao.destroyGame(id);
    }
}