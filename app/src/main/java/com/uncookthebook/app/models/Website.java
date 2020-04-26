package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Models a Website.
 */
@Getter
@ToString
public class Website implements Model {
    @SerializedName("name")
    private final String name;

    /**
     * Class constructor
     * @param name the website name
     */
    public Website(@NonNull String name) {
        this.name = name;
    }
}
