package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.dao.Game;
import com.foxobyte.steam_achievements.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByPlayers(Player player);
}
