package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NonNull;

/**
 * Models a User.
 */
@Getter
public final class User implements Model {
    @SerializedName("uid")
    private String uid;
    @SerializedName("name")
    private final String name;
    @SerializedName("surname")
    private final String surname;
    @SerializedName("email")
    private final String email;

    /**
     * Class constructor
     * @param uid the user's id
     * @param name the user's given name
     * @param surname the user's family name
     * @param email the user's email
     */
    public User(@NonNull String uid, @NonNull String name, @NonNull String surname, @NonNull String email) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
