package com.foxobyte.steam_achievements.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Settings {
    public static String API_KEY;

    @Autowired
    public void loadEnvironment(
            @Value("${env.steam.api-key}") String API_KEY
    ) {
        Settings.API_KEY = API_KEY;
    }

}
