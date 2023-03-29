package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.dao.Game;
import com.foxobyte.steam_achievements.dao.GameAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameAchievementRepository extends JpaRepository<GameAchievement, String> {
    Optional<GameAchievement> findBySteamNameAndGame(String steamName, Game game);

    List<GameAchievement> findByGame(Long appId);
}
