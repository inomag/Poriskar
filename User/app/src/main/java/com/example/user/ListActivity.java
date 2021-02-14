package com.example.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private BottomNavigationView bottomNavigationView;
    private ImageView selectedImage;
    private FloatingActionButton fab;
    private MaterialButton bt_submit;
    private Window window;
    String currentPhotoPath;
    StorageReference storageReference;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    Uri contentUri;
    File f;
    String imageFileName, link, id, user_route;
    TextInputEditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        user_route = intent.getStringExtra("route");
        storageReference= FirebaseStorage.getInstance().getReference();
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.app_bar_location);

        bottomNavigationView.setBackground(null);
        bottomAppBar = (BottomAppBar)findViewById(R.id.bottom_app_bar);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getParent(),
                    new String[]{
                            Manifest.permission.CAMERA
                    },100);}
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            showPostDialog();
        });
        initComponent();

    }

    private void showPostDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_post);
       selectedImage = (ImageView)dialog.findViewById(R.id.setImage);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        bt_submit = dialog.findViewById(R.id.bt_upload);

        editText = (TextInputEditText) dialog.findViewById(R.id.post_text);
        editText.addTextChangedListener(new TextWatcher() {
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
            Toast.makeText(getApplicationContext(),"Button Pressed",Toast.LENGTH_SHORT).show();
            if(f!=null){
                uploadImageToFirebase(f.getName(),contentUri);
            }
            if(imageFileName!=null){
                uploadImageToFirebase(imageFileName,contentUri);
            }
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Post Submitted", Toast.LENGTH_SHORT).show();
        });

        dialog.findViewById(R.id.bt_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions();
            }
        });

        dialog.findViewById(R.id.bt_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else {
                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                 f = new File(currentPhotoPath);
                     selectedImage.setImageURI(Uri.fromFile(f));
                Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));


                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                 contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                 contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                 imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri: " + imageFileName);
               selectedImage.setImageURI(contentUri);
            }
        }
    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        StorageReference image = storageReference.child("pictures/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(selectedImage);
                        Log.d("tag","Uploaded Image url is"+ uri.toString());
                        link = uri.toString();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> post = new HashMap<>();
                        post.put("address",editText.getText().toString());
                        post.put("image",link);
                        db.collection("marked_locations").add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                                putInUserCol();
                            }
                        });
                        Map<String,Object> sub = new HashMap<>();
                        sub.put("address",editText.getText().toString());
                        sub.put("image",link);
                        db.collection("users").document(id).collection("marked_places").add(sub);
                    }
                });
                Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void putInUserCol() {

    }


    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }




    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }




    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider1",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }


    @SuppressLint("ResourceAsColor")
    private void initComponent() {
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.app_bar_help:
                    Intent intent = new Intent(getApplicationContext(),HelpActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("route",user_route);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.app_bar_home:
                    Intent intent1 = new Intent(getApplicationContext(),HomeActivity.class);
                    intent1.putExtra("id",id);
                    intent1.putExtra("route",user_route);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.app_bar_location:
                    Intent intent2 = new Intent(getApplicationContext(),ListActivity.class);
                    intent2.putExtra("id",id);
                    intent2.putExtra("route",user_route);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.app_bar_profile:
                    Intent intent3 = new Intent(getApplicationContext(),ProfileActivity.class);
                    intent3.putExtra("id",id);
                    intent3.putExtra("route",user_route);
                    startActivity(intent3);
                    finish();
                    break;
            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    selectedFragment).commit();
            return true;
        });
    }

    public String getUserId() {
        return id;
    }


    public String getRoute() {
        return user_route;
    }
}