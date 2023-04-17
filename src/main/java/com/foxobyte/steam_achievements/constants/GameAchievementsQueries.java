package com.foxobyte.steam_achievements.constants;

public class GameAchievementsQueries {
    private static final String INSERT_GAME_ACHIEVEMENT = """
            INSERT INTO GAME_ACHIEVEMENTS (DESCRIPTION, DISPLAY_NAME, ICON, ICON_GRAY, STEAM_NAME, APP_ID) VALUES (:description, :displayName, :icon, :iconGray, :steamName, :appId);
            """;

    private static final String SELECT_GAME_ACHIEVEMENT = """
            SELECT * FROM GAME_ACHIEVEMENTS WHERE GAME_ACHIEVEMENT_ID = :gameAchievementId
            """;

    private static final String SELECT_GAME_ACHIEVEMENTS_BY_APP_ID = """
            SELECT * FROM GAME_ACHIEVEMENTS WHERE APP_ID = :appId
            """;

    private static final String SELECT_ALL_GAME_ACHIEVEMENTS = """
            SELECT * FROM GAME_ACHIEVEMENTS;
            """;

    private static final String UPDATE_GAME_ACHIEVEMENT = """
            UPDATE GAMES SET 
                DESCRIPTION = :description, 
                DISPLAY_NAME = :displayName, 
                ICON = :icon, 
                ICON_GRAY = :iconGray, 
                STEAM_NAME = :steamName, 
                APP_ID = :appId
            WHERE GAME_ACHIEVEMENT_ID = :gameAchievementId
            """;

    private static final String DELETE_GAME_ACHIEVEMENT = """
            DELETE FROM GAME_ACHIEVEMENTS WHERE GAME_ACHIEVEMENT_ID = :gameAchievementId
            """;
}
