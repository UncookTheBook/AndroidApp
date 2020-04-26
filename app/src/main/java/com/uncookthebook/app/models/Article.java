package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Models an Article.
 */
@Getter
@ToString
public class Article implements Model {
    @SerializedName("url")
    private final String url;
    @SerializedName("name")
    private final String name;
    @SerializedName("positive_reports")
    private final Integer positiveReports;
    @SerializedName("negative_reports")
    private final Integer negativeReports;

    /**
     * Class constructor
     * @param url the article's url
     * @param name the article's name
     * @param positiveReports the number of positive reports
     * @param negativeReports the number of negative reports
     */
    public Article(@NonNull String url, @NonNull String name, @NonNull Integer positiveReports, @NonNull Integer negativeReports) {
        this.url = url;
        this.name = name;
        this.positiveReports = positiveReports;
        this.negativeReports = negativeReports;
    }
}
