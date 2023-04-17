package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.model.Game;
import com.foxobyte.steam_achievements.model.GameAchievement;
import com.foxobyte.steam_achievements.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameAchievementJpa extends JpaRepository<GameAchievement, String> {
    Optional<GameAchievement> findBySteamNameAndGame(String steamName, Game game);

    List<GameAchievement> findByGameAndPlayers(Game game, Player player);
}
