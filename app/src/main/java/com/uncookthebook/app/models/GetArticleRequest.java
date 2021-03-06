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
public class GetArticleRequest implements Request {
    @SerializedName("url")
    private final String url;
    @SerializedName("website_name")
    private final String websiteName;

    /**
     * Class constructor
     * @param url the article's url
     * @param websiteName the website's name
     */
    public GetArticleRequest(@NonNull String url, @NonNull String websiteName) {
        this.url = url;
        this.websiteName = websiteName;
    }
}
