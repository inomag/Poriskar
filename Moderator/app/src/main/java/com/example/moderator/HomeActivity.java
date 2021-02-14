package com.example.moderator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private TabLayout tab_layout;
    private NestedScrollView nested_scroll_view;
    private BottomNavigationView bottomNavigationView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView route_head, route_info;
    private LottieAnimationView home_anim;
    Long phone;
    ExtendedFloatingActionButton start, cancel;

    private static final String CHANNEL_ID = "driver_notification";
    private static final String CHANNEL_NAME = "Route Assigned";
    private static final String CHANNEL_DESC ="routes assigned by the admin";


    Long myPhone;
    Button start_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
       phone = intent.getLongExtra("phone", 0);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.home_appbar);
        bottomNavigationView.setBackground(null);
        //    bottomNavigationView.setItemIconTintList(null);

        start_btn= findViewById(R.id.start_btn);


        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(),Welcome.class);
//                startActivity(i);
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.social_appbar:
                       Intent intent1 = new Intent(getApplicationContext(),Social.class);
                       intent1.putExtra("phone",phone);
                       startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.help_appbar:

                        Intent intent2 = new Intent(getApplicationContext(),Help.class);
                        intent2.putExtra("phone",phone);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.home_appbar:
                        Intent intent3 = new Intent(getApplicationContext(),HomeActivity.class);
                        intent3.putExtra("phone",phone);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });


                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        Toast.makeText(getApplicationContext(), String.valueOf(phone), Toast.LENGTH_SHORT).show();
        route_head = findViewById(R.id.route_head);
        route_info = findViewById(R.id.route_info);
        start = findViewById(R.id.start_btn);
        cancel = findViewById(R.id.cancel_btn);
        home_anim = findViewById(R.id.home_anim);
        driverRoute();

    }


    private void driverRoute() {
        db.collection("drivers").whereEqualTo("phone_no",phone).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<DocumentSnapshot> docs = task.getResult().getDocuments();
                    DocumentSnapshot doc = docs.get(0);
                    db.collection("drivers").document(doc.getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(value!=null){
                                String route = "";
                                route = value.getString("route");

                                if(Integer.parseInt(route)==0){
                                    home_anim.setAnimation(R.raw.done);
                                    home_anim.playAnimation();

                                    route_head.setText("No Routes Left");
                                    route_info.setText("");
                                    start.setEnabled(false);
                                    cancel.setEnabled(false);
                                }else{
                                    showNotifications("Route Assigned","Route No "+route);
                                    home_anim.setAnimation(R.raw.home);
                                    home_anim.playAnimation();
                                    route_head.setText("Route Assigned");
                                    route_info.setText("Route No "+route);
                                    start.setEnabled(true);
                                    cancel.setEnabled(true);
                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DocumentReference ref = db.collection("drivers").document(doc.getId());
                                            ref.update("route","0");
                                        }
                                    });
                                }
                            }


                        }
                    });



                }
            }
        });
    }

    private void showNotifications(String title,String body){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_pin)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_MAX);

        Intent notificationIntent = new Intent(getApplicationContext().getApplicationContext(), HomeActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext().getApplicationContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        NotificationManagerCompat mGR = NotificationManagerCompat.from(getApplicationContext());
        mGR.notify(1,mBuilder.build());
    }
}


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.homeactivity, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        } else {
//            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//}