package com.foxobyte.steam_achievements.controller;

import com.foxobyte.steam_achievements.client.steam.model.SteamGame;
import com.foxobyte.steam_achievements.model.Game;
import com.foxobyte.steam_achievements.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/games")
@CrossOrigin
public class GameController {
    @Autowired
    GameService service;

    @GetMapping
    public List<Game> getPlayerGames() {
        return service.getGames();
    }

    @GetMapping("/test")
    public List<SteamGame> getSteamGames() {
        try {
            return service.getSteamGames();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(System.out::println);

            return null;
        }
    }
}
