package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.dao.Game;
import com.foxobyte.steam_achievements.dao.GameAchievement;
import com.foxobyte.steam_achievements.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GameAchievementRepository extends JpaRepository<GameAchievement, String> {
    Optional<GameAchievement> findBySteamNameAndGame(String steamName, Game game);

    List<GameAchievement> findByGameAndPlayers(Game game, Player player);
}
