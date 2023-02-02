package com.example.quizapp.network;

import com.example.quizapp.RegisterActivity;
import com.example.quizapp.model.Contest;
import com.example.quizapp.model.Ranking;
import com.example.quizapp.model.RegisterResponse;
import com.example.quizapp.model.UserRegister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterFace {
    @GET("/questions/getContestById/{id}")
    Call<Contest> getById(@Path("id") String id);

    @GET("/contest/getAllContest")
    Call<List<Contest>> getAllContests();

    @GET("/ranking/getLeaderBoard/{contestId}")
    Call<Ranking> getLeaderBoardByContestId(@Path("contestId") String contestId);


    @POST("api/auth/register")
    Call<RegisterResponse> registerUser(@Body UserRegister userRegister);
}
