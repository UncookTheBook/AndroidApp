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

    @SerializedName("user_position")
    private final int userPosition;

    /**
     * Class constructor
     * @param leaderboard the leaderboard
     * @param userPosition the user Position
     */
    public GetLeaderboardResponse(List<LeaderboardUser> leaderboard, int userPosition) {
        this.leaderboard = leaderboard;
        this.userPosition = userPosition;
    }
}
