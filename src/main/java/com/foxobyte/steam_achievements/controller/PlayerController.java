package com.foxobyte.steam_achievements.controller;

import com.foxobyte.steam_achievements.dao.Player;
import com.foxobyte.steam_achievements.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.foxobyte.steam_achievements.util.PrettyPrint.print;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    @Autowired
    PlayerService service;

    @GetMapping
    public ResponseEntity<Player> getPlayerAchievements(
            @RequestParam("steamId") Long steamId
    ) {
        try {
            return new ResponseEntity<>(service.getPlayer(steamId), HttpStatus.OK);
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
            return new ResponseEntity<>(service.updatePlayerAchievements(steamId), HttpStatus.OK);
        } catch (Exception e) {
            print(e);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
