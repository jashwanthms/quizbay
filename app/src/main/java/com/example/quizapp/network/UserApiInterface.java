package com.example.quizapp.network;
import com.example.quizapp.model.ContestSave;

import com.example.quizapp.model.Status;
import com.example.quizapp.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiInterface {

    @POST("saveContext/{userId}")
    Call<Status> putContext(@Path("userId") String userId, @Body ContestSave contestSave);

    @POST("getQuestionResponse")
    Call<Integer> getQuestionResponse(@Body UserResponse userResponse);
}
