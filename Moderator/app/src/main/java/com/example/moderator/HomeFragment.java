package com.example.moderator;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.List;

public class HomeFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView route_head, route_info;
    private LottieAnimationView home_anim;
    Long phone;
    ExtendedFloatingActionButton start, cancel;

    private static final String CHANNEL_ID = "driver_notification";
    private static final String CHANNEL_NAME = "Route Assigned";
    private static final String CHANNEL_DESC ="routes assigned by the admin";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView =inflater.inflate(R.layout.fragment_home, container, false);

//        HomeActivity activity = (HomeActivity)getActivity();
//
//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription(CHANNEL_DESC);
//            NotificationManager manager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
//            manager.createNotificationChannel(channel);
//        }
//        phone =activity.getMyData();
//        Toast.makeText(activity, String.valueOf(phone), Toast.LENGTH_SHORT).show();
//        route_head = RootView.findViewById(R.id.route_head);
//        route_info = RootView.findViewById(R.id.route_info);
//        start = RootView.findViewById(R.id.start_btn);
//        cancel = RootView.findViewById(R.id.cancel_btn);
//        home_anim = RootView.findViewById(R.id.home_anim);
//        driverRoute();
        return RootView;
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
                new NotificationCompat.Builder(getActivity(),CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_pin)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_MAX);

        Intent notificationIntent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message");

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        NotificationManagerCompat mGR = NotificationManagerCompat.from(getActivity());
        mGR.notify(1,mBuilder.build());
    }


}