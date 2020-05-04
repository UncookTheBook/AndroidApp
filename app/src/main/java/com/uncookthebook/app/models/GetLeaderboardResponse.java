package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

/**
 * Models a GetLeaderboardResponse.
 */
@Getter
@ToString
public class GetLeaderboardResponse {
    @SerializedName("leaderboard")
    private final List<LeaderboardUser> leaderboard;

    /**
     * Class constructor
     * @param leaderboard the leaderboard
     */
    public GetLeaderboardResponse(List<LeaderboardUser> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
