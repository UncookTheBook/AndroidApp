package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

/**
 * Models a GetLeaderboardRequest
 */
@Getter
@ToString
public class GetLeaderboardRequest implements Request {
    @SerializedName("type")
    private final LeaderboardType leaderboardType;

    /**
     * Class constructor
     * @param leaderboardType the leaderboard type. See {@link LeaderboardType}
     */
    public GetLeaderboardRequest(LeaderboardType leaderboardType) {
        this.leaderboardType = leaderboardType;
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
