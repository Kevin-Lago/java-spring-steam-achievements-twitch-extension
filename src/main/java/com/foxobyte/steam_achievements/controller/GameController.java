package com.foxobyte.steam_achievements.controller;

import com.foxobyte.steam_achievements.dao.Game;
import com.foxobyte.steam_achievements.dao.Player;
import com.foxobyte.steam_achievements.service.GameService;
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

//    @GetMapping
//    public ResponseEntity<List<Game>> getPlayerGames(
//            @RequestParam("steamId") String steamId
//    ) {
//        try {
//            return new ResponseEntity<>(service.getGamesWithAchievements(steamId), HttpStatus.OK);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping
//    public ResponseEntity<List<Game>> getGames() {
//        try {
//            return new ResponseEntity<>(service.getGames(), HttpStatus.OK);
//        } catch (Exception e) {
//            print(e);
//
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PutMapping
//    public ResponseEntity<Player> updateSteamOwnedGames(
//            @RequestParam("steamId") Long steamId
//    ) {
//        try {
//            return new ResponseEntity<>(service.updatePlayerGames(steamId), HttpStatus.OK);
//        } catch (Exception e) {
//            print(e);
//
//            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
