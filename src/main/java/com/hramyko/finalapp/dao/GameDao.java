package com.hramyko.finalapp.dao;

import com.hramyko.finalapp.entity.Game;

import java.util.List;

public interface GameDao {
    List<Game> findAllGames();
    Game findGameById(int id);
    void saveGame(Game game);
    void updateGame(int id, Game game);
    void destroyGame(int id);
}
