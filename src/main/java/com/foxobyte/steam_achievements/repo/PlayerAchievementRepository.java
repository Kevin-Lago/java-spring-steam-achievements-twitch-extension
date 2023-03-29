package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.dao.Player;
import com.foxobyte.steam_achievements.dao.PlayerAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerAchievementRepository extends JpaRepository<PlayerAchievement, Long> {
    Optional<PlayerAchievement> findByAchievedAndUnlockTimeAndGameAchievementIdAndPlayer(Boolean achieved, String unlockTimed, Long gameAchievementId, Player player);
}
