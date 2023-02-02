package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText FirstName,LastName,Email,DateOfBirth,Password,PlatformId,PhoneNumber,UserName;

    private Button RegisterButton,LoginButton;
    ApiInterFace apiInterFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirstName=findViewById(R.id.et_first_name);
        LastName=findViewById(R.id.et_lastname);
        Email=findViewById(R.id.et_email);
        DateOfBirth=findViewById(R.id.et_dob);
        Password=findViewById(R.id.et_password);
        PhoneNumber=findViewById(R.id.et_phone);
        UserName=findViewById(R.id.et_username);
        RegisterButton=findViewById(R.id.btn_createuser);
        LoginButton=findViewById(R.id.btn_login_user);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegister userRegister = new UserRegister();
                userRegister.setFirstName(FirstName.getText().toString());
                userRegister.setLastName(LastName.getText().toString());
                userRegister.setEmail(Email.getText().toString());
                userRegister.setDob(DateOfBirth.getText().toString());
                userRegister.setPassword(Password.getText().toString());
                userRegister.setPlatformId("quiz");
                userRegister.setPhoneNumber(PhoneNumber.getText().toString());
                userRegister.setUsername(UserName.getText().toString());
                apiInterFace=((ApplicationClass)getApplication()).registerRetrofit.create(ApiInterFace.class);
                apiInterFace.registerUser(userRegister).enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        RegisterResponse registerResponse = response.body();
                        Log.e("token", registerResponse.getUserId());
                        //     ((TextView) findViewById(R.id.tv_access)).setText(registerResponse.getUserId());
                        Toast.makeText(RegisterActivity.this, registerResponse.getUserId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });
            }
        });


    }
}