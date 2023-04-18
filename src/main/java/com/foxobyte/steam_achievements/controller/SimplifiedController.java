package com.foxobyte.steam_achievements.controller;

import com.foxobyte.steam_achievements.client.steam.model.SteamPlayerDetails;
import com.foxobyte.steam_achievements.model.GameAchievement;
import com.foxobyte.steam_achievements.model.Player;
import com.foxobyte.steam_achievements.service.SimplifiedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/simp")
@CrossOrigin
public class SimplifiedController {
    @Autowired
    SimplifiedService service;

    @GetMapping("/player")
    public ResponseEntity<Player> getPlayerDetails(
            @RequestParam("steamId") Long steamId
    ) {
        try {
            return new ResponseEntity<>(service.getPlayer(steamId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/achievements")
    public ResponseEntity<List<GameAchievement>> getPlayerAchievements(
            @RequestParam("steamId") Long steamId,
            @RequestParam("appId") Long appId
    ) {
        try {
            return new ResponseEntity<>(service.getPlayerAchievements(steamId, appId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
