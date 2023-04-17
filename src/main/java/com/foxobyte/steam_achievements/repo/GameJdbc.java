package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.client.steam.model.SteamGame;
import com.foxobyte.steam_achievements.model.Game;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.foxobyte.steam_achievements.constants.GamesQueries.*;

@Repository
public class GameJdbc implements GameDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public GameJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Game addGame(SteamGame game) {
        try {
            jdbcTemplate.update(
                    INSERT_GAME,
                    game.getAppId(),
                    game.getImgIconUrl(),
                    game.getName()
            );

            return getGame(game.getAppId());
        } catch (DuplicateKeyException e) {
            return getGame(game.getAppId());
        }
    }

    @Override
    public Game getGame(Long appId) {
        Game game = jdbcTemplate.queryForObject(
                SELECT_GAME,
                this::mapToGame,
                appId
        );

        return game;
    }

    @Override
    public List<Game> getPlayerGames(Long steamId) {
        List<Game> playerGames = jdbcTemplate.query(
                SELECT_PLAYER_GAMES,
                this::mapToGame,
                steamId
        );

        return playerGames;
    }

    @Override
    public List<Game> getAllGames() {
        return null;
    }

    @Override
    public Game updateGame(SteamGame steamGame) {
        return null;
    }

    @Override
    public void deleteGame(Long appId) {

    }

    private Game mapToGame(ResultSet rs, int rowNum) throws SQLException {
        Game game = new Game();
        game.setAppId(rs.getLong("app_id"));
        game.setName(rs.getString("name"));
        game.setImageUrl(rs.getString("image_url"));

        return game;
    }
}
