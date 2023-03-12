package com.foxobyte.steam_achievements.model;

import java.util.List;

public class Game {
    private String name;
    private String imageId;
    private List<Achievement> achievements;

    public Game(String name, String imageId, List<Achievement> achievements) {
        this.name = name;
        this.imageId = imageId;
        this.achievements = achievements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }
}
