package com.example.lob;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.RequestOptions;
import com.example.lob.DTO.UserDto;
import com.example.lob.Service.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.loader.content.CursorLoader;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    private  Storage storage;
    private StorageReference storageReference;
    private static final int PICK_FROM_ALBUM=1;
    private FirebaseAuth  googleAuth = FirebaseAuth.getInstance();
    public static Context CONTEXT;
    private  FirebaseUser  currentUser = googleAuth.getCurrentUser();
    TextView usermail;
    ImageView userImg;
    ImageView butoon_logout;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        super.onCreate(savedInstanceState);
        CONTEXT=this;
        setContentView(R.layout.user);
        butoon_logout=findViewById(R.id.button_logout);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        userImg=findViewById(R.id.userImg);
        usermail=findViewById(R.id.userEmail);
        onRestart();

        butoon_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // switch (requestCode) {
       //     case PICK_FROM_ALBUM: { // 코드 일치
        //        storage=new Storage();
         //       storage.UploadProfile2( getPath(data.getData()),currentUser.getUid());
          //      onResume();
         //   }

    }
    public String getPath(Uri uri ){
        String [] proj ={MediaStore.Images.Media.DATA};
        Cursor cursor =null;
        int index;
        String name = null;
        CursorLoader cursorLoader= new CursorLoader(this.getApplicationContext(),uri,proj,null,null,null);
        if(cursorLoader!=null) {
            cursor = cursorLoader.loadInBackground();
            index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            name=cursor.getString(index);
            cursor.close();
        }
        return name;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(currentUser!=null){
            usermail.setText(currentUser.getEmail().substring(0,currentUser.getEmail().lastIndexOf("@"))+"님");
            storageReference=firebaseStorage.getReference().child("/profile/"+currentUser.getUid());
            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Log.e("isTrue","zxczczxczxczxc");
                        Glide.with(UserActivity.this)
                                .load(task.getResult())
                                .apply(RequestOptions.circleCropTransform())
                                .into(userImg);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Storage storage = new Storage();
                    Uri drawablePath = getURLForResource(R.drawable.normal_profile);
                    storage.UploadProfile(drawablePath,currentUser.getUid());
                    onResume();
                }
            });

        }    }
    @Override
    public synchronized  void onResume() {
        super.onResume();
        updateProfile();
    }

    public  void updateProfile(){
        if(currentUser!=null){
            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Log.e("isTrue","zxczczxczxczxc");
                        Glide.with(UserActivity.this)
                                .load(task.getResult())
                                .apply(RequestOptions.circleCropTransform())
                                .into(userImg);
                    }
                }
            });
        }
    }
    private Uri getURLForResource(int resId) {
        Resources resources = CONTEXT.getResources();
    return     Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(resId) + '/' + resources.getResourceTypeName(resId) + '/' + resources.getResourceEntryName(resId) );
    }
}


