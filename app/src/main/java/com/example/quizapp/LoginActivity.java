package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapp.application.ApplicationClass;
import com.example.quizapp.model.LoginCredentials;
import com.example.quizapp.model.RegisterResponse;
import com.example.quizapp.model.UserRegister;
import com.example.quizapp.network.ApiInterFace;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText UserName,Password;
    private Button LoginButton,RegisterButton;

    private ApiInterFace apiInterFace;

    private SharedPreferences sharedPreferences,getLoginPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserName=findViewById(R.id.et_login_uname);
        Password=findViewById(R.id.et_login_password);
        LoginButton=findViewById(R.id.btn_login);
        RegisterButton=findViewById(R.id.btn_register);
        apiInterFace=((ApplicationClass)getApplication()).registerRetrofit.create(ApiInterFace.class);


        try {
            getLoginPreferences = getSharedPreferences("LoginCredentialsPref", MODE_PRIVATE);
            if (!TextUtils.isEmpty(getLoginPreferences.getString("username", ""))) {
                startActivity(new Intent(LoginActivity.this, ContestsActivity.class));
                finish();
            }
        }catch (Exception e)
        {

        }

         sharedPreferences = getSharedPreferences("LoginCredentialsPref",MODE_PRIVATE);

        SharedPreferences.Editor myEdit = sharedPreferences.edit();




        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginCredentials loginCredentials=new LoginCredentials();
                loginCredentials.setUsername(UserName.getText().toString());
                loginCredentials.setPassword(Password.getText().toString());


                apiInterFace.loginUser(loginCredentials).enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if(response.body()!=null)
                        {
                            Toast.makeText(LoginActivity.this,"logged in"+response.body().getUserId(), Toast.LENGTH_SHORT).show();


                            myEdit.putString("userId", response.body().getUserId());
                            myEdit.putString("refreshtoken", response.body().getRefreshToken());
                            myEdit.putString("username", UserName.getText().toString());
                            myEdit.apply();
                            startActivity(new Intent(LoginActivity.this,ContestsActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}