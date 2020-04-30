package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

/**
 * Models an AddFriendRequest
 */
@Getter
@ToString
public class AddFriendRequest implements Model {
    @SerializedName("uid")
    private final String userId;
    @SerializedName("friend_email")
    private final String friendEmail;

    /**
     * Class constructor
     * @param userId the user id
     * @param friendEmail the email of the friend that will become one of the users one
     */
    public AddFriendRequest(String userId, String friendEmail) {
        this.userId = userId;
        this.friendEmail = friendEmail;
    }
}
