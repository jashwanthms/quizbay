package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.application.ApplicationClass;
import com.example.quizapp.model.RegisterResponse;
import com.example.quizapp.model.UserRegister;
import com.example.quizapp.network.ApiInterFace;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ApiInterFace apiInterFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UserRegister userRegister = new UserRegister();
        userRegister.setFirstName("ruthvik");
        userRegister.setLastName("jakka");
        userRegister.setEmail("jakkaruthvik@gmail.com");
        userRegister.setDob("06/05/2001");
        userRegister.setPassword("12345678");
        userRegister.setPlatformId("quiz");
        userRegister.setPhoneNumber("8688988525");
        userRegister.setUsername("ruthvikquiz");

        apiInterFace=((ApplicationClass)getApplication()).registerRetrofit.create(ApiInterFace.class);
        apiInterFace.registerUser(userRegister).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                Log.e("token", registerResponse.getUserId());
                ((TextView) findViewById(R.id.tv_access)).setText(registerResponse.getUserId());
                Toast.makeText(RegisterActivity.this, registerResponse.getUserId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });

    }
}