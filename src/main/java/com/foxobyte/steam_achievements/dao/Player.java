package com.foxobyte.steam_achievements.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "players")
public class Player {
    @Id
    private Long steamId;
    private String steamName;
    private String profileUrl;
    private String avatarHash;
    private Long lastLogOff;
    private Long timeCreated;
    @ManyToMany
    @JoinTable(name = "games_players",
            joinColumns = {@JoinColumn(name = "steam_id")},
            inverseJoinColumns = {@JoinColumn(name = "app_id")})
    @JsonManagedReference(value = "player-games")
    private Set<Game> games;

    public Long getSteamId() {
        return steamId;
    }

    public void setSteamId(Long steamId) {
        this.steamId = steamId;
    }

    public String getSteamName() {
        return steamName;
    }

    public void setSteamName(String steamName) {
        this.steamName = steamName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getAvatarHash() {
        return avatarHash;
    }

    public void setAvatarHash(String avatarHash) {
        this.avatarHash = avatarHash;
    }

    public Long getLastLogOff() {
        return lastLogOff;
    }

    public void setLastLogOff(Long lastLogOff) {
        this.lastLogOff = lastLogOff;
    }

    public Long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> game) {
        this.games = game;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }
}
