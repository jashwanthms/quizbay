package com.example.quizapp.network;

import com.example.quizapp.model.Contest;
import com.example.quizapp.model.Ranking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterFace {
    @GET("/questions/getContestById/{id}")
    Call<Contest> getById(@Path("id") String id);

    @GET("/contest/getAllContest")
    Call<List<Contest>> getAllContests();

    @GET("/ranking/getLeaderBoard/{contestId}")
    Call<Ranking> getLeaderBoardByContestId(@Path("contestId") String contestId);
}
