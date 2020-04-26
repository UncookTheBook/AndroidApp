package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Models a GetArticle Request.
 */
@Getter
@ToString
public class GetArticleRequest implements Model {
    @SerializedName("url")
    private final String url;
    @SerializedName("website_name")
    private final String website_name;

    /**
     * Class constructor
     * @param url the article's url
     * @param website_name the website's name
     */
    public GetArticleRequest(@NonNull String url, @NonNull String website_name) {
        this.url = url;
        this.website_name = website_name;
    }
}
