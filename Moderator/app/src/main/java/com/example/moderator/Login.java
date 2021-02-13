package com.example.moderator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Login extends AppCompatActivity {

    MaterialButton button;
    TextInputEditText phoneNo;
    TextInputEditText password;
    String pass="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = findViewById(R.id.login_btn);
        password = (TextInputEditText)findViewById(R.id.pass_word);
        phoneNo = findViewById(R.id.phone_no);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneNo.getText().toString();
                Long l= Long.parseLong(phone);
                       pass= password.getText().toString();
                        FirebaseFirestore.getInstance().collection("drivers").whereEqualTo("phone_no",l).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    List<DocumentSnapshot> doc = task.getResult().getDocuments();
                                    String passcode = doc.get(0).getString("password");
                                    if (pass.equals(passcode)){
                                       Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                       intent.putExtra("phone",l);
                                       startActivity(intent);
                                       Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }




}
