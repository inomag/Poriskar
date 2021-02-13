package com.example.moderator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView route_head, route_info;
    private LottieAnimationView home_anim;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView =inflater.inflate(R.layout.fragment_home, container, false);
    String phone;

        HomeActivity activity = (HomeActivity)getActivity();
        phone=activity.getMyData();

        Toast.makeText(activity, phone, Toast.LENGTH_SHORT).show();

        route_head = RootView.findViewById(R.id.route_head);
        route_info = RootView.findViewById(R.id.route_info);

        home_anim = RootView.findViewById(R.id.home_anim);

        driverRoute();

        return RootView;

    }

    private void driverRoute() {
//        db.collection("drivers");
    }


}