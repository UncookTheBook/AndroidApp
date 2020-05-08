package com.uncookthebook.app.network;

import com.uncookthebook.app.models.AddFriendRequest;
import com.uncookthebook.app.models.GetArticleRequest;
import com.uncookthebook.app.models.GetArticleResponse;
import com.uncookthebook.app.models.GetLeaderboardRequest;
import com.uncookthebook.app.models.GetLeaderboardResponse;
import com.uncookthebook.app.models.SubmitReportRequest;
import com.uncookthebook.app.models.TokenizedRequest;
import com.uncookthebook.app.models.AddUserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * The Retrofit's API Service.
 * It contains all the APIs that are requested by the application.
 */
public interface APIService {

    @POST("/utb/add_user")
    Call<Void> addUser(@Body TokenizedRequest<AddUserRequest> request);

    @POST("/utb/get_article")
    Call<GetArticleResponse> getArticle(@Body TokenizedRequest<GetArticleRequest> request);

    @POST("/utb/submit_report")
    Call<String> submitReport(@Body TokenizedRequest<SubmitReportRequest> request);

    @POST("/utb/add_friend")
    Call<Void> addFriend(@Body TokenizedRequest<AddFriendRequest> request);

    @POST("/utb/get_leaderboard")
    Call<GetLeaderboardResponse> getLeaderboard(@Body TokenizedRequest<GetLeaderboardRequest> request);
}
