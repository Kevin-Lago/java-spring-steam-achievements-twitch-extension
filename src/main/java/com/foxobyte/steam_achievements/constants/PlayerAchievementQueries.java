package com.foxobyte.steam_achievements.constants;

public class PlayerAchievementQueries {
    private static final String INSERT_PLAYER_ACHIEVEMENT = """
            INSERT INTO PLAYER_ACHIEVEMENTS 
                (ACHIEVED, UNLOCK_TIME, GAME_ACHIEVEMENT_ID, STEAM_ID) 
            VALUES 
                (:achieved, :unlockTime, :gameAchievementId, :steamId);
            """;

    private static final String SELECT_PLAYER_ACHIEVEMENT = """
            SELECT * FROM PLAYER_ACHIEVEMENTS WHERE PLAYER_ACHIEVEMENT_ID = :playerAchievementId
            """;

    private static final String SELECT_PLAYER_ACHIEVEMENT_BY_GAME_ACHIEVEMENT_ID = """
            SELECT * FROM PLAYER_ACHIEVEMENTS WHERE GAME_ACHIEVEMENT_ID = :gameAchievementId
            """;

    private static final String SELECT_ALL_PLAYER_ACHIEVEMENTS = """
            SELECT * FROM PLAYER_ACHIEVEMENTS;
            """;

    private static final String UPDATE_PLAYER_ACHIEVEMENT = """
            UPDATE PLAYER_ACHIEVEMENTS SET 
            ACHIEVED = :achieved, 
            UNLOCK_TIME = :unlockTime, 
            GAME_ACHIEVEMENT_ID = :gameAchievementId, 
            STEAM_ID = :steamId
            WHERE PLAYER_ACHIEVEMENT_ID = :playerAchievementId
            """;

    private static final String DELETE_PLAYER_ACHIEVEMENT = """
            DELETE FROM PLAYER_ACHIEVEMENTS WHERE PLAYER_ACHIEVEMENT_ID = :playerAchievementId
            """;
}
