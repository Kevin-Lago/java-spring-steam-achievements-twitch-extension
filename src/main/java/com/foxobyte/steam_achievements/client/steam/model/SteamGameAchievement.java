package com.foxobyte.steam_achievements.client.steam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SteamGameAchievement {
    private final String name;
    private final String defaultValue;
    private final String displayName;
    private final Boolean hidden;
    private final String description;
    private final String icon;
    private final String iconGray;

    public SteamGameAchievement(
            @JsonProperty("name") String name,
            @JsonProperty("defaultvalue") String defaultValue,
            @JsonProperty("displayName") String displayName,
            @JsonProperty("hidden") Boolean hidden,
            @JsonProperty("description") String description,
            @JsonProperty("icon") String icon,
            @JsonProperty("icongray") String iconGray) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.displayName = displayName;
        this.hidden = hidden;
        this.description = description;
        this.icon = icon;
        this.iconGray = iconGray;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getIconGray() {
        return iconGray;
    }
}
