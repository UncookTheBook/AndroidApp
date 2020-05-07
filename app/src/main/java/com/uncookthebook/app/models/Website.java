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
    @SerializedName("legit")
    private final Double legitPercentage;

    /**
     * Class constructor
     * @param name the website name
     * @param legitPercentage the legit percentage of the website
     */
    public Website(@NonNull String name, @NonNull Double legitPercentage) {
        this.name = name;
        this.legitPercentage = legitPercentage;
    }
}
