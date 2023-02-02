package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quizapp.application.ApplicationClass;
import com.example.quizapp.model.UserData;
import com.example.quizapp.network.UserApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    UserApiInterface userApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userApiInterface = ((ApplicationClass) getApplication()).userRetrofit.create(UserApiInterface.class);
        userApiInterface.getUserData(getSharedPreferences("LoginCredentialsPref", MODE_PRIVATE).getString("userId", "")).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                UserData userData = response.body();
                ((TextView) findViewById(R.id.tv_profile_sports)).setText(userData.getIsSports()+"");
                ((TextView) findViewById(R.id.tv_profile_entertainment)).setText(userData.getIsEntertainment()+"");
                ((TextView) findViewById(R.id.tv_profile_fashion)).setText(userData.getIsFashion()+"");
                ((TextView) findViewById(R.id.tv_profile_food)).setText(userData.getIsFood()+"");
                ((TextView) findViewById(R.id.tv_profile_travel)).setText(userData.getIsTravel()+"");
                ((TextView) findViewById(R.id.tv_profile_music)).setText(userData.getIsMusic()+"");
                ((TextView) findViewById(R.id.tv_profile_email)).setText(userData.getQuizUserEmail());

                findViewById(R.id.bt_profile_sign_out).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteSharedPreferences("LoginCredentialsPref");
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {

            }
        });
    }
}