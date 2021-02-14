package com.example.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class HomeFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView head,info;
    private LottieAnimationView home_anim;
    private String id,route;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_home, container, false);
        HomeActivity activity = (HomeActivity)getActivity();
        id = activity.getUserId();
        route = activity.getRoute();
//        Toast.makeText(getActivity().getApplicationContext(),route,Toast.LENGTH_SHORT).show();

        head = RootView.findViewById(R.id.home_card_head);
        info = RootView.findViewById(R.id.driver_info);
        home_anim = RootView.findViewById(R.id.home_anim);

        updateCard();
        return RootView;
    }



    private void updateCard() {

        db.collection("routes").whereEqualTo("name",route).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            String driver="";
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        List<DocumentSnapshot> docs = task.getResult().getDocuments();
                        DocumentSnapshot doc = docs.get(0);

                        driver = doc.getString("driver");
//                        Toast.makeText(getActivity().getApplicationContext(),driver,Toast.LENGTH_SHORT).show();
                        db.collection("drivers").document(driver).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (value!=null){
//                                Toast.makeText(getActivity().getApplicationContext(),value.getString("name"),Toast.LENGTH_SHORT).show();
                                head.setText("Driver on the way");
                                info.setText(value.getString("name")+", is 100m away");
                                home_anim.setAnimation(R.raw.bell);
                                home_anim.playAnimation();


                            }

                        }
                    });
                    }
            }
        });



    }
}