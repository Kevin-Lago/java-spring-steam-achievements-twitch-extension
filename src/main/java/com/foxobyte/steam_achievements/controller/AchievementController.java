package com.foxobyte.steam_achievements.controller;

import com.foxobyte.steam_achievements.model.Achievement;
import com.foxobyte.steam_achievements.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
@CrossOrigin
public class AchievementController {
    @Autowired
    AchievementService service;

    @GetMapping
    public List<Achievement> getAchievements(String steamId, String appId) {
        return service.getAchievements(steamId, appId);
    }
}
