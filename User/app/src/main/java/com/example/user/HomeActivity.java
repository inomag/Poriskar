package com.example.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private BottomNavigationView bottomNavigationView;
    private ImageView image;
    private FloatingActionButton fab;
    private MaterialButton bt_submit;
    final Integer CAMERA=0,UPLOAD=1;
    private Window window;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        setUpBottomAppBar();
//        image = (ImageView)findViewById(R.id.imageview);

        window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));


        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);

        bottomAppBar = (BottomAppBar)findViewById(R.id.bottom_app_bar);



        if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },100);}
        //click event over FAB
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            showPostDialog();
        });

        initComponent();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    private void showPostDialog() {


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_post);

       image = (ImageView)dialog.findViewById(R.id.setImage);



        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        bt_submit = dialog.findViewById(R.id.bt_upload);
        ((EditText) dialog.findViewById(R.id.post_text)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bt_submit.setEnabled(!s.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        bt_submit.setOnClickListener(v -> {
            dialog.dismiss();
            // String s=editText.getText().toString();
            Toast.makeText(getApplicationContext(), "Post Submitted", Toast.LENGTH_SHORT).show();
        });

        dialog.findViewById(R.id.bt_photo).setOnClickListener(v ->
                takeImage());

        dialog.findViewById(R.id.bt_link).setOnClickListener(v ->
                uploadImage());
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @SuppressLint("IntentReset")
    private void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
        startActivityForResult(intent, UPLOAD);
    }



    private void takeImage() {





        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        startActivityForResult(i,CAMERA);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode== CAMERA) {
            try {
          //  Bundle bundle = data.getExtras();



            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            image.setImageBitmap(bitmap);


            }
            catch(Exception e){

                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
            }
        }
        else if(requestCode==UPLOAD){
            try {
                Uri selectedImageUri = data.getData();


            image.setImageURI(selectedImageUri);
            }
            catch (Exception e){

                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
            }
        }




    }

    @SuppressLint("ResourceAsColor")
    private void initComponent() {

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.app_bar_help:
                    selectedFragment = new HelpFragment();
                    break;
                case R.id.app_bar_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.app_bar_location:
                    selectedFragment = new ListFragment();
                    break;
                case R.id.app_bar_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        });
    }
}
