package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.dao.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
