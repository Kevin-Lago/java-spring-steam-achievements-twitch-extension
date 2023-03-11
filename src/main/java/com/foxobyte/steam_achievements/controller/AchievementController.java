package com.foxobyte.steam_achievements.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {
    @GetMapping
    public String getAchievements(String steamId) {
        return "test";
    }
}
