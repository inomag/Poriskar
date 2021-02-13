package com.example.moderator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private TextView textView;
    private CharSequence charSequence;
    private int index;
    long delay = 200;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
//        button = (Button)findViewById(R.id.continue_btn);
//        button.setOnClickListener(v -> {
//
//        });
        textView=(TextView)findViewById(R.id.app_name);
        animateText("PORISKAR ");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, StartScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        },4000);
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            textView.setText(charSequence.subSequence(0,index++));
            if(index<charSequence.length()){
                handler.postDelayed(runnable,delay);
            }
        }
    };

    public void animateText(CharSequence cs){
        charSequence = cs;
        index=0;
        textView.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }
}