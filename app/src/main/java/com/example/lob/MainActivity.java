package com.example.lob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.lob.DTO.UserDto;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SignInButton signInGoogleButton;
    private FirebaseAuth googleAuth = null;
    private GoogleSignInClient GoogleSignInClient = null;
    private GoogleSignInOptions gso = null;
    private static final int Google_RC_SIGN_IN = 9001;
    private UserDto  userDto= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.signInGoogleButton).setOnClickListener(this);


        signInGoogleButton = findViewById(R.id.signInGoogleButton);
        //google 다음과 같이 GoogleSignInOptions 객체를 구성할 때 requestIdToken을 호출합니다.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleAuth = FirebaseAuth.getInstance();

    }
    @Override
    public void onStart() {
        super.onStart();
        signOut();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = googleAuth.getCurrentUser();
        Log.e("asdasdsada", String.valueOf(currentUser));
        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Google_RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) { //구글버튼 로그인 누르고, 구글사용자 확인되면 실행되는 로직
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account); //구글이용자 확인된 사람정보 파이어베이스로 넘기기
            } else {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "로그인실패", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.e( "asdasd","firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        googleAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = googleAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
           userDto=new UserDto(user.getEmail(),user.getUid());
            Bundle bundle= new Bundle();
            bundle.putSerializable("user", (Serializable) userDto);
            //여기에 유저 정보 담기
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "LoginError", Toast.LENGTH_SHORT).show();
        }
    }


    private void signIn() {
        Intent signInIntent = GoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Google_RC_SIGN_IN);
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signInGoogleButton) {
            signIn();
        }
    }
}
