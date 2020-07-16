package com.example.couscousapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);


        Toolbar tbReader = (Toolbar) findViewById(R.id.tb_reader);
        setSupportActionBar(tbReader);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);

        WebView webView = findViewById(R.id.reader_webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getIntent().getStringExtra("external_url"));

        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tb_reader_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rate:
                // User chose the "Sort" item, show the app settings UI...
                Log.i("Info", "Button 'Rate' pressed");
                confirmDialog();
                return true;

            case R.id.comment:
                // User chose the "Comment" item, show the app settings UI...
                Log.i("Info", "Button 'Comment' pressed");
                return true;

            case R.id.favorite:
                // User chose the "Favorite" item, show the app settings UI...
                Log.i("Info", "Button 'Favorite' pressed");
                return true;

            case R.id.browser:
                // User chose the "Open in Browser" item
                Log.i("Info", " 'Open in Browser' pressed");
                String url = getIntent().getStringExtra("external_url");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Beispiel: ConfirmDialog Rating");
        builder.setMessage("Möchten Sie diesen Artikel als 'Fake News' deklarieren?");
        builder.setCancelable(false);
        builder.setPositiveButton("Lügenpresse!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 Log.i("Info", "Danke Merkel!");
            }
        });

        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Info", "Artikel ist glaubwürdig.");
            }
        });

        builder.show();
    }
}
