package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Models an object that includes a verification token.
 * @param <T> the object Type
 */
@Getter
public class TokenizedRequest<T extends Request> {
    @SerializedName("token")
    private String token;
    @SerializedName("object")
    private T object;
    /**
     * Class constructor
     * @param token the verification token
     * @param object the actual body object
     */
    public TokenizedRequest(String token, T object) {
        this.token = token;
        this.object = object;
    }
}
