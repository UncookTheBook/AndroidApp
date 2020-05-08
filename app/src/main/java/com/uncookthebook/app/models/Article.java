package com.uncookthebook.app.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Models an Article.
 */
@Getter
@ToString
public class Article implements Request {
    @SerializedName("url")
    private final String url;
    @SerializedName("name")
    private final String name;
    @SerializedName("legit_reports")
    @Setter //we need the setter when the user has already submitted a report
    private Integer legitReports;
    @SerializedName("fake_reports")
    @Setter //we need the setter when the user has already submitted a report
    private Integer fakeReports;

    /**
     * Class constructor
     * @param url the article's url
     * @param name the article's name
     * @param legitReports the number of positive reports
     * @param fakeReports the number of negative reports
     */
    public Article(@NonNull String url, @NonNull String name, @NonNull Integer legitReports, @NonNull Integer fakeReports) {
        this.url = url;
        this.name = name;
        this.legitReports = legitReports;
        this.fakeReports = fakeReports;
    }
}
