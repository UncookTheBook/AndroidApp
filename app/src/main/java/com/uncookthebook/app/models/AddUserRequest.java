package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Models an AddUserRequest.
 */
@Getter
@ToString
public class AddUserRequest implements Request {
    @SerializedName("name")
    private final String name;
    @SerializedName("email")
    private final String email;

    /**
     * Class constructor
     * @param name the user's given name
     * @param email the user's email
     */
    public AddUserRequest(@NonNull String name, @NonNull String email) {
        this.name = name;
        this.email = email;
    }
}
