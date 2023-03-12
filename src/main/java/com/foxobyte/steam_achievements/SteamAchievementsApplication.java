package com.foxobyte.steam_achievements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SteamAchievementsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SteamAchievementsApplication.class, args);
	}

}
