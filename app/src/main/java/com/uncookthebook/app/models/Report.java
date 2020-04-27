package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

/**
 * Models a Report.
 */
@Getter
@ToString
public class Report implements Model {
    @SerializedName("url")
    private final String articleUrl;
    @SerializedName("uid")
    private final String userId;
    @SerializedName("report")
    private final ReportValue reportValue;

    /**
     * Class constructor
     * @param articleUrl the article url
     * @param userId the user id
     * @param reportValue the report value
     */
    public Report(String articleUrl, String userId, ReportValue reportValue) {
        this.articleUrl = articleUrl;
        this.userId = userId;
        this.reportValue = reportValue;
    }

    public enum ReportValue {
        @SerializedName("L")
        LEGIT,
        @SerializedName("F")
        FAKE
    }
}
