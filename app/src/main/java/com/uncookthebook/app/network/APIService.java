package com.uncookthebook.app.network;

import com.uncookthebook.app.models.AddFriendRequest;
import com.uncookthebook.app.models.GetArticleRequest;
import com.uncookthebook.app.models.GetArticleResponse;
import com.uncookthebook.app.models.GetLeaderboardRequest;
import com.uncookthebook.app.models.GetLeaderboardResponse;
import com.uncookthebook.app.models.LeaderboardUser;
import com.uncookthebook.app.models.Report;
import com.uncookthebook.app.models.TokenizedObject;
import com.uncookthebook.app.models.User;

import java.util.List;

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

    @POST("/utb/add_friend")
    Call<Void> addFriend(@Body TokenizedObject<AddFriendRequest> request);

    @POST("/utb/get_leaderboard")
    Call<GetLeaderboardResponse> getLeaderboard(@Body TokenizedObject<GetLeaderboardRequest> request);
}
