package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
