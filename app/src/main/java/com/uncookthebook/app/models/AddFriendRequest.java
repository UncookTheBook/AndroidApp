package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Models an AddFriendRequest
 */
@Getter
@ToString
public class AddFriendRequest implements Request {
    @SerializedName("friend_email")
    private final String friendEmail;

    /**
     * Class constructor
     * @param friendEmail the email of the friend that will become one of the users one
     */
    public AddFriendRequest(@NonNull String friendEmail) {
        this.friendEmail = friendEmail;
    }
}
