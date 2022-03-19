package com.example.whatsappbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class setting_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();
    }
}