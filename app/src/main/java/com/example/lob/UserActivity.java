package com.example.lob;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lob.DTO.UserDto;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private  UserDto user;
    TextView usermail;
    ImageView userImg;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        userImg=findViewById(R.id.userImg);
        usermail=findViewById(R.id.userEmail);
        //img 둥글게
        Glide.with(this)
                .load(R.drawable.main)
                .apply(RequestOptions.circleCropTransform())
                .into(userImg);
        Intent intent=getIntent();
        user= (UserDto)intent.getSerializableExtra("user");
        usermail.setText(user.getUserMail().substring(0,user.getUserMail().lastIndexOf("@"))+"님");

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
