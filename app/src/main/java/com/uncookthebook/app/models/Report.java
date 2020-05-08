package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Models a Report.
 */
@Getter
@ToString
public class Report {
    @SerializedName("user_id")
    private final String userId;
    @SerializedName("article_url")
    private final String articleUrl;
    @SerializedName("value")
    private final ReportValue value;

    /**
     * Class constructor
     * @param userId the id of the user who submitted the report
     * @param articleUrl the url of the reported article
     * @param value the value of the report
     */
    public Report(@NonNull String userId, @NonNull String articleUrl, ReportValue value) {
        this.userId = userId;
        this.articleUrl = articleUrl;
        this.value = value;
    }
}
