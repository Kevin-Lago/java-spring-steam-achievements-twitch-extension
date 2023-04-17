package com.foxobyte.steam_achievements.constants;

public class PlayerQueries {
    private static final String INSERT_PLAYER = """
            INSERT INTO GAMES 
                (STEAM_ID, AVATAR_HASH, LAST_LOG_OFF, PROFILE_URL, STEAM_NAME, TIME_CREATED) 
            VALUES 
                (:steamId, :avatarHash, :lastLogOff, :profileUrl, :steamName, :timeCreated);
            """;

    private static final String SELECT_PLAYER = """
            SELECT * FROM PLAYERS WHERE STEAM_ID = :steamId
            """;

    private static final String SELECT_ALL_PLAYERS = """
            SELECT * FROM PLAYERS;
            """;

    private static final String UPDATE_PLAYER = """
            UPDATE PLAYERS SET 
            STEAM_ID = :steamId, 
            AVATAR_HASH = :avatarHash, 
            LAST_LOG_OFF = :lastLogOff, 
            PROFILE_URL = :profileUrl, 
            STEAM_NAME = :steamName, 
            TIME_CREATED = :timeCreated
            WHERE STEAM_ID = :steamId
            """;

    private static final String DELETE_PLAYER = """
            DELETE FROM PLAYERS WHERE STEAM_ID = :steamId
            """;
}
