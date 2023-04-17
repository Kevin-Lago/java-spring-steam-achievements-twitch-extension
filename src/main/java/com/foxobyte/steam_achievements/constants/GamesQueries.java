package com.foxobyte.steam_achievements.constants;

public class GamesQueries {
    public static final String INSERT_GAME = """
            INSERT INTO GAMES (APP_ID, IMAGE_URL, NAME) VALUES (:appId, :imageUrl, :name);
            """;

    public static final String SELECT_GAME = """
            SELECT * FROM GAMES WHERE APP_ID = :appId
            """;

    public static final String SELECT_ALL_GAMES = """
            SELECT * FROM GAMES;
            """;

    public static final String UPDATE_GAME = """
            UPDATE GAMES SET IMAGE_URL = :imageUrl, NAME = :name WHERE APP_ID = :appId
            """;

    public static final String DELETE_GAME = """
            DELETE FROM GAMES WHERE APP_ID = :appId
            """;
}
