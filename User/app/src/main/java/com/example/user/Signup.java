package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    private MaterialButton continue_to_map,to_login;
    private TextInputEditText name, phoneNo, password;

    String user_name,user_pass,phone_no,id;
    Long user_phone;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        continue_to_map = (MaterialButton)findViewById(R.id.continue_to_map);

        name = (TextInputEditText) findViewById(R.id.name_signup);
        phoneNo = (TextInputEditText) findViewById(R.id.phone_signup);
        password = (TextInputEditText) findViewById(R.id.pass_word_signup);


        user_name = name.getText().toString();
        user_pass = password.getText().toString();
//            phone_no = phoneNo.getText().toString();
//            user_phone = Long.parseLong(phone_no);

        continue_to_map.setOnClickListener(v -> {
         //   addData();
            startActivity(new Intent(Signup.this,MapMarker.class));
        });

        to_login = (MaterialButton)findViewById(R.id.back_to_login);
        to_login.setOnClickListener(v -> {
            startActivity(new Intent(Signup.this,Login.class));
        });


    }

//    private void addData() {
//        Map<String, Object> user = new HashMap<>();
//        user.put("name",user_name);
//        user.put("phone_no",user_phone);
//        user.put("password",user_pass);
//
//        db.collection("users").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//                if (task.isSuccessful()){
//                    id = task.getResult().getId();
//                    Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }


}