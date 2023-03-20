package com.foxobyte.steam_achievements.client.steam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SteamGame {
    @JsonProperty("appid")
    private Long appId;
    private String name;
    @JsonProperty("playtime_2weeks")
    private Integer playTime2Weeks;
    @JsonProperty("playtime_forever")
    private Integer totalPlayTime;
    @JsonProperty("img_icon_url")
    private String imgIconUrl;
    @JsonProperty("has_community_visible_stats")
    private Boolean hasCommunityVisibleStats;
    @JsonProperty("playtime_windows_forever")
    private Integer totalWindowsPlayTime;
    @JsonProperty("playtime_mac_forever")
    private Integer totalMacPlayTime;
    @JsonProperty("playtime_linux_forever")
    private Integer totalLinuxPlayTime;
    @JsonProperty("rtime_last_played")
    private Integer timeLastPlayed;
    @JsonProperty("content_descriptorids")
    private List<Integer> contentDescriptorids;
    @JsonProperty("has_leaderboards")
    private Boolean hasLeaderboards;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlayTime2Weeks() {
        return playTime2Weeks;
    }

    public void setPlayTime2Weeks(Integer playTime2Weeks) {
        this.playTime2Weeks = playTime2Weeks;
    }

    public Integer getTotalPlayTime() {
        return totalPlayTime;
    }

    public void setTotalPlayTime(Integer totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

    public String getImgIconUrl() {
        return imgIconUrl;
    }

    public void setImgIconUrl(String imgIconUrl) {
        this.imgIconUrl = imgIconUrl;
    }

    public Boolean getHasCommunityVisibleStats() {
        return hasCommunityVisibleStats;
    }

    public void setHasCommunityVisibleStats(Boolean hasCommunityVisibleStats) {
        this.hasCommunityVisibleStats = hasCommunityVisibleStats;
    }

    public Integer getTotalWindowsPlayTime() {
        return totalWindowsPlayTime;
    }

    public void setTotalWindowsPlayTime(Integer totalWindowsPlayTime) {
        this.totalWindowsPlayTime = totalWindowsPlayTime;
    }

    public Integer getTotalMacPlayTime() {
        return totalMacPlayTime;
    }

    public void setTotalMacPlayTime(Integer totalMacPlayTime) {
        this.totalMacPlayTime = totalMacPlayTime;
    }

    public Integer getTotalLinuxPlayTime() {
        return totalLinuxPlayTime;
    }

    public void setTotalLinuxPlayTime(Integer totalLinuxPlayTime) {
        this.totalLinuxPlayTime = totalLinuxPlayTime;
    }

    public Integer getTimeLastPlayed() {
        return timeLastPlayed;
    }

    public void setTimeLastPlayed(Integer timeLastPlayed) {
        this.timeLastPlayed = timeLastPlayed;
    }

    public List<Integer> getContentDescriptorids() {
        return contentDescriptorids;
    }

    public void setContentDescriptorids(List<Integer> contentDescriptorids) {
        this.contentDescriptorids = contentDescriptorids;
    }

    public Boolean getHasLeaderboards() {
        return hasLeaderboards;
    }

    public void setHasLeaderboards(Boolean hasLeaderboards) {
        this.hasLeaderboards = hasLeaderboards;
    }
}
