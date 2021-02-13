package com.example.moderator;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class HomeActivity extends AppCompatActivity {

    private TabLayout tab_layout;
    private NestedScrollView nested_scroll_view;
    private BottomNavigationView bottomNavigationView;


    String myString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent=getIntent();
        myString=intent.getStringExtra("phone");

        initComponent();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    public String getMyData(){
        return myString;
    }


    private void initComponent() {
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);


        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);
    //    bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.help_appbar:
                    selectedFragment = new HelpFragment();
                    break;
                case R.id.social_appbar:
                    selectedFragment = new SocialFragment();
                    break;
                case R.id.home_appbar:
                    selectedFragment = new HomeFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homeactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}