package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Models a SubmitReportRequest.
 */
@Getter
@ToString
public class SubmitReportRequest implements Request {
    @SerializedName("url")
    private final String articleUrl;
    @SerializedName("report")
    private final ReportValue reportValue;

    /**
     * Class constructor
     * @param articleUrl the article url
     * @param reportValue the report value
     */
    public SubmitReportRequest(@NonNull String articleUrl, ReportValue reportValue) {
        this.articleUrl = articleUrl;
        this.reportValue = reportValue;
    }
}
