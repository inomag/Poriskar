package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class Signup extends AppCompatActivity {

    private MaterialButton continue_to_map,to_login;
    private EditText name, phoneNo, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        continue_to_map = (MaterialButton)findViewById(R.id.continue_to_map);
        continue_to_map.setOnClickListener(v -> {
//            startActivity(new Intent(Signup.this,MapMarker.class));
        });

        to_login = (MaterialButton)findViewById(R.id.back_to_login);
        to_login.setOnClickListener(v -> {
            startActivity(new Intent(Signup.this,Login.class));
        });

        name = (EditText)findViewById(R.id.name_signup);
        phoneNo = (EditText)findViewById(R.id.phone_signup);
        password = (EditText)findViewById(R.id.pass_word_signup);

    }
}