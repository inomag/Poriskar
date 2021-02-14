package com.example.moderator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class SocialFragment extends Fragment {

    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;

    private FirestoreRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        mFirestoreList = getActivity().findViewById(R.id.recycler_view);
         FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Query
        Query query = db.collection("approved_locations");

        //RecyclerOptions
        FirestoreRecyclerOptions<PlaceModel> options = new FirestoreRecyclerOptions.Builder<PlaceModel>().setQuery(query,PlaceModel.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<PlaceModel, PlacesViewHolder>(options) {
            @NonNull
            @Override
            public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
                return new PlacesViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PlacesViewHolder holder, int position, @NonNull PlaceModel model) {
                holder.address.setText(model.getAddress());
                Glide.with(holder.image.getContext()).load(PlaceModel.image).into(holder.image);
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mFirestoreList.setAdapter(adapter);

        //ViewHolder






        return inflater.inflate(R.layout.fragment_social, container, false);


    }

    private class PlacesViewHolder extends  RecyclerView.ViewHolder {

        private TextView address;
        private ImageView image;
        public PlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.post_address);
            image = itemView.findViewById(R.id.img_post);


        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}