package com.hramyko.finalapp.service;

import com.hramyko.finalapp.entity.Game;

import java.util.List;

public interface GameService {
    List<Game> findGames();
    Game findGameById(int id);
    void saveGame(String jsonString);
    void updateGame(int id, String jsonString);
    void destroyGame(int id);
}
