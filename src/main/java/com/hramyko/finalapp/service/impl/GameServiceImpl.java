package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.entity.Game;
import com.hramyko.finalapp.repository.GameRepository;
import com.hramyko.finalapp.service.GameService;
import com.hramyko.finalapp.service.parser.JsonParser;
import com.hramyko.finalapp.service.validator.GameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameValidator gameValidator;

    @Autowired
    GameServiceImpl(GameRepository gameRepository, GameValidator gameValidator) {
        this.gameRepository = gameRepository;
        this.gameValidator = gameValidator;
    }

    @Override
    public List<Game> findGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game findGameById(int id) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isPresent()) {
            return game.get();
        } else throw new RuntimeException("Game with such id doesn't exists");
    }

    @Override
    public void saveGame(String jsonString) {
        Game game = (Game) JsonParser.getObjectFromJson(jsonString, Game.class.getName());
        if (game != null) {
            gameValidator.validateTitle(game.getTitle());
            gameRepository.save(game);
        } else throw new RuntimeException("Error of saving");
    }

    @Override
    public void updateGame(int id, String jsonString) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        Game game;
        Game newGame = (Game) JsonParser.getObjectFromJson(jsonString, Game.class.getName());
        if (optionalGame.isPresent()) {
            game = optionalGame.get();
        } else throw new RuntimeException("Game with such id doesn't exists");
        if(newGame != null) {
            gameValidator.validateTitle(newGame.getTitle());
            game.setTitle(newGame.getTitle());
            gameRepository.save(game);
        } else throw new RuntimeException("Error of updating");
    }

    @Override
    public void destroyGame(int id) {
        gameRepository.deleteById(id);
    }
}
