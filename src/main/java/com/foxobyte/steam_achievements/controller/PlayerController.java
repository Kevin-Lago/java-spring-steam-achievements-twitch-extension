package com.foxobyte.steam_achievements.controller;

import com.foxobyte.steam_achievements.dao.Player;
import com.foxobyte.steam_achievements.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static com.foxobyte.steam_achievements.util.PrettyPrint.print;

@RestController
@RequestMapping("/api/player")
@CrossOrigin
public class PlayerController {
    @Autowired
    PlayerService service;

    @GetMapping
    public ResponseEntity<Object> getPlayer(
            @RequestParam("steamId") Long steamId
    ) {
        try {
            return new ResponseEntity<>(service.getPlayer(steamId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            print(e.getMessage());

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            print(e.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/games")
    public ResponseEntity<Object> getPlayerGames(
            @RequestParam("steamId") Long steamId
    ) {
        try {
            return new ResponseEntity<>(service.getPlayerAndPlayerGames(steamId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/achievements")
    public ResponseEntity<Object> getPlayerAchievements(
            @RequestParam("steamId") Long steamId,
            @RequestParam("appId") Long appId
    ) {
        try {
            return new ResponseEntity<>(service.getPlayerAchievements(steamId, appId), HttpStatus.OK);
        } catch (Exception e) {
            print(e);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Player> updatePlayerAchievements(
            @RequestParam("steamId") Long steamId
    ) {
        try {
            return new ResponseEntity<>(service.initializePlayer(steamId), HttpStatus.OK);
        } catch (Exception e) {
            print(e);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
