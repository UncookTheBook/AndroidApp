package com.uncookthebook.app.network;

import com.uncookthebook.app.models.GetArticleRequest;
import com.uncookthebook.app.models.GetArticleResponse;
import com.uncookthebook.app.models.Report;
import com.uncookthebook.app.models.TokenizedObject;
import com.uncookthebook.app.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * The Retrofit's API Service.
 * It contains all the APIs that are requested by the application.
 */
public interface APIService {

    @POST("/utb/add_user")
    Call<Void> addUser(@Body TokenizedObject<User> request);

    @POST("/utb/get_article")
    Call<GetArticleResponse> getArticle(@Body TokenizedObject<GetArticleRequest> request);

    @POST("/utb/submit_report")
    Call<String> submitReport(@Body TokenizedObject<Report> request);
}
