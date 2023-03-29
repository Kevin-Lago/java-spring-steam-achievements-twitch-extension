package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.dao.Game;
import com.foxobyte.steam_achievements.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByPlayers(Player player);

//    Optional<Game> findByAppIdAndPlayer(Long appId, Player player);
}
