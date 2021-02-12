package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MapMarker extends AppCompatActivity implements OnMapReadyCallback{

    private ArrayList<String> routes;
    private String[] arr;

    private GoogleMap mMap;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;

    private MaterialButton continue_to_home;
    private AutoCompleteTextView spinner;
    private TextInputLayout spinner_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_marker);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        spinner_layout = (TextInputLayout)findViewById(R.id.spinner_layout);

        arr = getResources().getStringArray(R.array.route_names);

        routes = new ArrayList<>();
        routes.addAll(Arrays.asList(arr));


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.route,routes);

        spinner = (AutoCompleteTextView)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setThreshold(1);


        continue_to_home = (MaterialButton)findViewById(R.id.done_btn);
        continue_to_home.setOnClickListener(v -> {
//            startActivity(new Intent(MapMarker.this, HomeActivity.class));
//            finish();
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
