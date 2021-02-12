package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class StartScreen extends AppCompatActivity {

    private Button continue_btn;
    private TextView quote;
    private CharSequence charSequence;
    int index;
    long delay=20;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        quote = (TextView)findViewById(R.id.quote);
        animateText("Some quote on Cleanliness or usability of this app ");


        continue_btn = (Button)findViewById(R.id.continue_to_login);
        continue_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });

    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            quote.setText(charSequence.subSequence(0,index++));
            if(index<charSequence.length()){
                handler.postDelayed(runnable,delay);
            }
        }
    };

    public void animateText(CharSequence cs){
        charSequence = cs;
        index=0;
        quote.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }
}