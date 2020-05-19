package com.example.lob;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
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
import com.example.lob.UI.basket.BasketFragment;
import com.example.lob.UI.board.BoardFragment;
import com.example.lob.UI.calendar.CalendarFragment;
import com.example.lob.UI.consumption.ConsumptionFragment;
import com.example.lob.UI.cooking.CookingFragment;
import com.example.lob.UI.diet.DietFragment;
import com.example.lob.UI.favorite.FavoriteFragment;
import com.example.lob.UI.home.HomeFragment;
import com.example.lob.UI.refrigerator.RefrigeratorFragment;
import com.example.lob.UI.settings.SettingsFragment;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
    Toolbar toolbar;


    private RefrigeratorFragment refrigeratorFragment = new RefrigeratorFragment();
    private BasketFragment basketFragment = new BasketFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private CookingFragment cookingFragment = new CookingFragment();
    private FavoriteFragment favoriteFragment = new FavoriteFragment();

    private DrawerLayout mDrawerLayout;
    private Context context = this;

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
        NavigationView navView_toolbar = findViewById(R.id.navView);
        userImg=findViewById(R.id.userImg);
        usermail=findViewById(R.id.userEmail);

        onRestart();

        toolbar = (Toolbar) findViewById(R.id.toolbar); //툴바설정
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                menuItem.setChecked(true);
//                mDrawerLayout.closeDrawers();
//
//                int id = menuItem.getItemId();
//                String title = menuItem.getTitle().toString();
//
//                if(id == R.id.nav_refrigerator){
//                    Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
//                }
//                else if(id == R.id.nav_basket){
//                    Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
//                }
//                else if(id == R.id.nav_calendar){
//                    Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
//                }
//
//                return true;
//            }
//        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        butoon_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent1=new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}


