package com.example.couscousapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.couscousapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginProcess(View view) {

        //LOGIN

        Log.i("Info", "Login successful!");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
}
