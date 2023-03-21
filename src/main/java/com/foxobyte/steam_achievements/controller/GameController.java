package com.foxobyte.steam_achievements.controller;

import com.foxobyte.steam_achievements.dao.Game;
import com.foxobyte.steam_achievements.dao.Player;
import com.foxobyte.steam_achievements.service.GameService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.foxobyte.steam_achievements.util.PrettyPrint.print;

@RestController
@RequestMapping("api/games")
@CrossOrigin
public class GameController {
    @Autowired
    GameService service;

    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAllGames() {
        try {
            return new ResponseEntity<>(service.getAllGames(), HttpStatus.OK);
        } catch (Exception e) {
            print(e);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Game>> getPlayerGames(
            @RequestParam("steamId") Long steamId
    ) {
        try {
            return new ResponseEntity<>(service.getPlayerGames(steamId), HttpStatus.OK);
        } catch (Exception e) {
            print(e);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
