package com.example.couscousapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail;
    EditText userPassword;
    Button buttonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void loginProcess(View view) {

        //LOGIN

        Log.i("Info","Login successful!");

        Intent intent = new Intent(this, LandingPage.class);
        startActivity(intent);


    }
}
