package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query(value = """
            select p1_0.steam_id,p1_0.avatar_hash,p1_0.last_log_off,p1_0.profile_url,p1_0.steam_name,p1_0.time_created from players p1_0 where p1_0.steam_id=?
            """, nativeQuery = true)
    Optional<Player> findJustPlayerById(Long steamId);
}
