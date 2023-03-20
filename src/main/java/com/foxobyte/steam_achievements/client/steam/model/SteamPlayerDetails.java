package com.foxobyte.steam_achievements.client.steam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings(value = "unchecked")
public class SteamPlayerDetails {
    private final Long steamId;
    private final Integer communityVisibilityState;
    private final Integer profileStats;
    private final String personaName;
    private final Integer commentPermission;
    private final String profileUrl;
    private final String avatar;
    private final String avatarMedium;
    private final String avatarFull;
    private final String avatarHash;
    private final Long lastLogOff;
    private final Integer personaState;
    private final String primaryClanId;
    private final Long timeCreated;
    private final Integer personaStateFlags;

    public SteamPlayerDetails(
            @JsonProperty("steamid") Long steamId,
            @JsonProperty("communityvisibilitystate") Integer communityVisibilityState,
            @JsonProperty("profilestate") Integer profileStats,
            @JsonProperty("personaname") String personaName,
            @JsonProperty("commentpermission") Integer commentPermission,
            @JsonProperty("profileurl") String profileUrl,
            @JsonProperty("avatar") String avatar,
            @JsonProperty("avatarmedium") String avatarMedium,
            @JsonProperty("avatarfull") String avatarFull,
            @JsonProperty("avatarhash") String avatarHash,
            @JsonProperty("lastlogoff") Long lastLogOff,
            @JsonProperty("personastate") Integer personaState,
            @JsonProperty("primaryclanid") String primaryClanId,
            @JsonProperty("timecreated") Long timeCreated,
            @JsonProperty("personastateflags") Integer personaStateFlags
    ) {
        this.steamId = steamId;
        this.communityVisibilityState = communityVisibilityState;
        this.profileStats = profileStats;
        this.personaName = personaName;
        this.commentPermission = commentPermission;
        this.profileUrl = profileUrl;
        this.avatar = avatar;
        this.avatarMedium = avatarMedium;
        this.avatarFull = avatarFull;
        this.avatarHash = avatarHash;
        this.lastLogOff = lastLogOff;
        this.personaState = personaState;
        this.primaryClanId = primaryClanId;
        this.timeCreated = timeCreated;
        this.personaStateFlags = personaStateFlags;
    }

    public Long getSteamId() {
        return steamId;
    }

    public Integer getCommunityVisibilityState() {
        return communityVisibilityState;
    }

    public Integer getProfileStats() {
        return profileStats;
    }

    public String getPersonaName() {
        return personaName;
    }

    public Integer getCommentPermission() {
        return commentPermission;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAvatarMedium() {
        return avatarMedium;
    }

    public String getAvatarFull() {
        return avatarFull;
    }

    public String getAvatarHash() {
        return avatarHash;
    }

    public Long getLastLogOff() {
        return lastLogOff;
    }

    public Integer getPersonaState() {
        return personaState;
    }

    public String getPrimaryClanId() {
        return primaryClanId;
    }

    public Long getTimeCreated() {
        return timeCreated;
    }

    public Integer getPersonaStateFlags() {
        return personaStateFlags;
    }
}
