package com.example.quizapp.application;

import android.app.Application;

import com.example.quizapp.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationClass extends Application {
    public Retrofit retrofit;
    @Override
    public void onCreate() {
        super.onCreate();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(Constants.BASE_URL).client(new OkHttpClient()).build();

    }
}
