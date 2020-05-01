package com.example.lob;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lob.DTO.UserDto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth gooleAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    TextView usermail;
    ImageView userImg;
    ImageView butoon_logout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        butoon_logout=findViewById(R.id.button_logout);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        userImg=findViewById(R.id.userImg);
        usermail=findViewById(R.id.userEmail);
        gooleAuth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference().child("/profile/"+gooleAuth.getUid());
        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Log.e("TTTTT","zxczczxczxczxc");
                    Glide.with(UserActivity.this)
                            .load(task.getResult())
                          .apply(RequestOptions.circleCropTransform())
                            .into(userImg);
                }
            }
        });

        butoon_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gooleAuth.signOut();
                Intent intent1=new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

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

