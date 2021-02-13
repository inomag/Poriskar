package com.example.user;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.user.directionhelpers.FetchURL;
import com.example.user.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MapMarker extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, TaskLoadedCallback {

    private ArrayList<String> routes = new ArrayList<>();
    private List<String> arr;

    private GoogleMap mMap;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;

    private MaterialButton continue_to_home;
    private AutoCompleteTextView spinner;
    private TextInputLayout spinner_layout;
    private List<DocumentSnapshot> doc;

    String id;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private int ACCESS_LOCATION_REQUEST_CODE = 10001;
    FusedLocationProviderClient fusedLocationProviderClient;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Double srcLat = 27.233984;
    private Double srcLon = 94.102248;
    private MarkerOptions source, destination;

    private Polyline currentPolyline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_marker);


        Intent intent = getIntent();

        id = intent.getStringExtra("id");

        // centerMapOnMyLocation();
        //  onMapReady(mMap);


        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));


        getRouteList();

        source = new MarkerOptions().position(new LatLng(srcLat,srcLon)).title("Source");


        spinner_layout = (TextInputLayout) findViewById(R.id.spinner_layout);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.route, routes);

        spinner = (AutoCompleteTextView) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setThreshold(1);



        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Toast.makeText(MapMarker.this, "Hello", Toast.LENGTH_SHORT).show();

                    showPolyLine(position);

                }
                catch (Error e){
                    Log.e("Error",e.getMessage());
                    Log.e("Error","------------------------------");
                }

            }
        });




        continue_to_home = (MaterialButton) findViewById(R.id.done_btn);
        continue_to_home.setOnClickListener(v -> {
//            startActivity(new Intent(MapMarker.this, HomeActivity.class));
//            finish();
        });

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void showPolyLine(int position) {

        db.collection("routes").whereEqualTo("name",routes.get(position)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> temp = task.getResult().getDocuments();

                Double dest_lat = temp.get(0).getDouble("latitude");
                Double dest_lon = temp.get(0).getDouble("longitude");
                LatLng dest = new LatLng(dest_lat,dest_lon);
                destination = new MarkerOptions().position(dest).title("Destination");
                mMap.clear();

                    mMap.addMarker(destination);
                    mMap.addMarker(source);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dest,15));
                    new FetchURL(MapMarker.this).execute(getUrl(source.getPosition(),destination.getPosition(),"driving"),"driving");
//                if (task.isSuccessful()){
//
//
//                    Toast.makeText(getApplicationContext(),dest_lat.toString()+", "+dest_lon.toString(),Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), "ddjfuhfhuf", Toast.LENGTH_SHORT).show();
////                    destination = new MarkerOptions().position(dest).title("Destination");
////                    mMap.clear();
////                    mMap.addMarker(destination);
////                    mMap.addMarker(source);
////                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dest,15));
////                    new FetchURL(MapMarker.this).execute(getUrl(source.getPosition(),destination.getPosition(),"driving"),"driving");
//                }
            }
        });

    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
    }

    private void getRouteList() {
        db.collection("routes").whereNotEqualTo("name","Source").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    doc = task.getResult().getDocuments();
                    List<String> arr_route = null;

                    for (int i =0;i<doc.size();i++){
                        String s = doc.get(i).get("name").toString();
                        routes.add(i,s);
                    }
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation1();
            zoomToUserLocation1();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);

            }
        }


    }

    private void zoomToUserLocation1() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Network ISSUE", Toast.LENGTH_SHORT).show();
            return;
        } else {
            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            } catch (Exception ex) {
            }


            if (!gps_enabled ) {
                new AlertDialog.Builder(getApplicationContext()).setMessage(R.string.gps_network_not_enabled)
                        .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getApplicationContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        });


            } else {
                Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
                locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        mMap.addMarker(new MarkerOptions().position(latLng));
                    }
                });
            }
        }
    }

    private void enableUserLocation1() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Unable to get Location",Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            mMap.setMyLocationEnabled(true);
        }


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            LocationManager locationManager= (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try{
                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            }catch(Exception ex){}
            try{
                network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }catch (Exception ex){}

            if(!gps_enabled && !network_enabled){
                new AlertDialog.Builder(getApplicationContext()).setMessage(R.string.gps_network_not_enabled)
                        .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getApplicationContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        }).setNegativeButton(R.string.Cancel,null).show();

            }
            else {
                enableUserLocation1();
                zoomToUserLocation1();
            }
        }
        else
        {

        }
    }

//    private static final int COLOR_BLACK_ARGB = 0xff000000;
//    private static final int POLYLINE_STROKE_WIDTH_PX = 12;
//
//    private void stylePolyline(Polyline polyline) {
//        String type = "";
//        // Get the data object stored with the polyline.
//        if (polyline.getTag() != null) {
//            type = polyline.getTag().toString();
//        }
//
//        polyline.setStartCap(new RoundCap());
//        polyline.setEndCap(new RoundCap());
//        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
//        polyline.setColor(COLOR_BLACK_ARGB);
//        polyline.setJointType(JointType.ROUND);
//    }

    @Override
    public void onTaskDone(Object... values) {

//        stylePolyline(currentPolyline);
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
