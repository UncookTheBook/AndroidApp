package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

/**
 * Models a GetLeaderboardRequest
 */
@Getter
@ToString
public class GetLeaderboardRequest implements Model {
    @SerializedName("type")
    private final LeaderboardType leaderboardType;
    @SerializedName("uid")
    private final String userId;

    /**
     * Class constructor
     * @param leaderboardType the leaderboard type. See {@link LeaderboardType}
     * @param userId the user id
     */
    public GetLeaderboardRequest(LeaderboardType leaderboardType, String userId) {
        this.leaderboardType = leaderboardType;
        this.userId = userId;
    }

    /**
     * Leaderboard type
     */
    public enum LeaderboardType {
        /**
         * Global leaderboard
         */
        @SerializedName("GLOBAL")
        GLOBAL,
        /**
         * Friends only leaderboard
         */
        @SerializedName("FRIENDS")
        FRIENDS
    }
}
