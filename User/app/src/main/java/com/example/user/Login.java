package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.button.MaterialButton;

public class Login extends AppCompatActivity {

    private MaterialButton login_btn,signup_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        signup_btn = (MaterialButton)findViewById(R.id.continue_to_signup);
        signup_btn.setOnClickListener(v -> {
            startActivity(new Intent(Login.this,Signup.class));
        });

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(v ->{
//            startActivity(new Intent(Login.this, HomeActivity.class));
//            finish();
        });

    }



}

