package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

/**
 * Models a LeaderboardUser
 */
@Getter
@ToString
public class LeaderboardUser {
    @SerializedName("name")
    private final String name;
    @SerializedName("score")
    private final Integer score;

    /**
     * Class constructor
     * @param name the user name
     * @param score the user score
     */
    public LeaderboardUser(String name, Integer score) {
        this.name = name;
        this.score = score;
    }
}
