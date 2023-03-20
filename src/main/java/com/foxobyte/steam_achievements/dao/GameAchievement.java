package com.foxobyte.steam_achievements.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "game_achievements")
public class GameAchievement {
    @Id
    @GeneratedValue
    private Long id;
    private String steamName;
    private String displayName;
    private String description;
    private String icon;
    private String iconGray;
    @ManyToOne
    @JoinColumn(name = "app_id", nullable = false)
    @JsonBackReference(value = "game-achievements")
    private Game game;
    @OneToOne(mappedBy = "gameAchievement")
    @JsonManagedReference(value = "player-achievement")
    @AttributeOverrides(value = {
            @AttributeOverride(name = "achieved", column = @Column(name = "achieved")),
            @AttributeOverride(name = "unlockedTime", column = @Column(name = "unlocked_time"))
    })
    @AssociationOverrides(value = {
            @AssociationOverride(name = "gameAchievement", joinColumns = @JoinColumn(name = "game_achievement_id"))
    })
    private PlayerAchievement playerAchievement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSteamName() {
        return steamName;
    }

    public void setSteamName(String steamName) {
        this.steamName = steamName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconGray() {
        return iconGray;
    }

    public void setIconGray(String iconGray) {
        this.iconGray = iconGray;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public PlayerAchievement getPlayerAchievement() {
        return playerAchievement;
    }

    public void setPlayerAchievement(PlayerAchievement playerAchievement) {
        this.playerAchievement = playerAchievement;
    }
}
