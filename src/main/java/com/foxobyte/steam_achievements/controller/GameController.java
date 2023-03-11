package com.foxobyte.steam_achievements.controller;

import com.foxobyte.steam_achievements.client.steam.model.SteamGame;
import com.foxobyte.steam_achievements.model.Game;
import com.foxobyte.steam_achievements.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/games")
public class GameController {
    @Autowired
    GameService service;

    @GetMapping
    public List<Game> getPlayerGames() {
        return null;
    }

    @GetMapping("/test")
    public List<SteamGame> getSteamGames() {
        return service.getSteamGames();
    }
}
