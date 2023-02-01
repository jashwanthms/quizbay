package com.example.quizapp.network;

import com.example.quizapp.model.Contest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterFace {
    @GET("getContestById/{id}")
    Call<Contest> getById(@Path("id") String id);
}
