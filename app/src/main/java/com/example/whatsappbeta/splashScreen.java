package com.example.whatsappbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.window.SplashScreen;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splashScreen extends AppCompatActivity {
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_splash_screen);
        auth=FirebaseAuth.getInstance();
        onStart();
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            finish();
            Intent intent=new Intent(splashScreen.this,MainActivity.class);
            startActivity(intent);
        }
        else{
            finish();
            Intent intent=new Intent(splashScreen.this,signUp.class);
            startActivity(intent);
        }
   }
}