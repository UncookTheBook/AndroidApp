package com.uncookthebook.app.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Models a GetArticle Response. It contains the Article, its Website and the report
 * for the article if the user submitted it. The report is null in case the user didn't
 * already report the article.
 */
@Getter
@ToString
public class GetArticleResponse {
    @SerializedName("article")
    private final Article article;
    @SerializedName("website")
    private final Website website;
    @SerializedName("report")
    private final Report report;

    /**
     * Class constructor
     * @param article the article
     * @param website the article's website
     */
    public GetArticleResponse(@NonNull Article article, @NonNull Website website, Report report) {
        this.article = article;
        this.website = website;
        this.report = report;
    }

    @Nullable
    public Report getReport() {
        return report;
    }
}
