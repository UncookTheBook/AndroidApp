package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Models a GetArticle Response. It contains the Article and its Website.
 */
@Getter
@ToString
public class GetArticleResponse {
    @SerializedName("article")
    private final Article article;
    @SerializedName("website")
    private final Website website;

    /**
     * Class constructor
     * @param article the article
     * @param website the article's website
     */
    public GetArticleResponse(@NonNull Article article, @NonNull Website website) {
        this.article = article;
        this.website = website;
    }
}
