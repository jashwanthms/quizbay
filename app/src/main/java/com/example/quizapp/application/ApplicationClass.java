package com.example.quizapp.application;

import android.app.Application;

import com.example.quizapp.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationClass extends Application {
    public Retrofit retrofit,userRetrofit,leaderBoardRetrofit;
    @Override
    public void onCreate() {
        super.onCreate();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        userRetrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(Constants.USER_URL).client(new OkHttpClient()).build();


        retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(Constants.BASE_URL).client(new OkHttpClient()).build();
        leaderBoardRetrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(Constants.LEADER_BASE_URL).client(new OkHttpClient()).build();
    }
}
