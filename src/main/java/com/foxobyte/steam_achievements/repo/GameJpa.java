package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.model.Game;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameJpa extends JpaRepository<Game, Long> {
//    Game addGame(Game game);
//
//    Game getGame(Long appId);

    @Query(value = """
                select 
                    g1_0.steam_id,
                    g1_1.app_id,
                    g1_1.image_url,
                    g1_1.name 
                from games_players 
                    g1_0 join games g1_1 on 
                    g1_1.app_id=g1_0.app_id 
                where 
                    g1_0.steam_id=:steamId
            """, nativeQuery = true)
    List<Game> findPlayerGamesBySteamId(@Param("steamId") Long steamId);
//    Optional<Game> findByAppIdAndPlayer(Long appId, Player player);
}
