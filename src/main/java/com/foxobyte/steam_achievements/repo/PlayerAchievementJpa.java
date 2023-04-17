package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.model.GameAchievement;
import com.foxobyte.steam_achievements.model.Player;
import com.foxobyte.steam_achievements.model.PlayerAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerAchievementJpa extends JpaRepository<PlayerAchievement, Long> {
    Optional<PlayerAchievement> findByGameAchievement(GameAchievement gameAchievement);

    Optional<PlayerAchievement> findByAchievedAndUnlockTimeAndGameAchievementIdAndPlayer(Boolean achieved, String unlockTimed, Long gameAchievementId, Player player);
}
