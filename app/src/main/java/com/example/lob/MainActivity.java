package com.example.lob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.common.SignInButton;

public class MainActivity extends AppCompatActivity {
//android:background="#dddddd 흰샛이
// signInGoogleButton
private SignInButton signInGoogleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInGoogleButton=findViewById(R.id.signInGoogleButton);


    }
}
