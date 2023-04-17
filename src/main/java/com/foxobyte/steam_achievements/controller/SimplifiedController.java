package com.foxobyte.steam_achievements.controller;

import com.foxobyte.steam_achievements.client.steam.model.SteamPlayerDetails;
import com.foxobyte.steam_achievements.model.Player;
import com.foxobyte.steam_achievements.service.SimplifiedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/simp")
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
}
