package com.example.couscousapp.activity;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.couscousapp.R;
import com.example.couscousapp.api.JsonPlaceHolderApi;
import com.example.couscousapp.json_model.Data;
import com.example.couscousapp.json_model.Rating;
import com.example.couscousapp.json_model.RatingPojo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReaderActivity extends AppCompatActivity {

    private static final String TAG = "ReaderActivity";

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

/*        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });*/


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
            /*case R.id.rate:
                // User chose the "Sort" item, show the app settings UI...
                Log.i("Info", "Button 'Rate' pressed");
                confirmDialog();
                return true;*/

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

//    public void confirmDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Beispiel: ConfirmDialog Rating");
//        builder.setMessage("Möchten Sie diesen Artikel als 'Fake News' deklarieren?");
//        builder.setCancelable(false);
//        builder.setPositiveButton("Lügenpresse!", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                 Log.i("Info", "Danke Merkel!");
//            }
//        });
//
//        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Log.i("Info", "Artikel ist glaubwürdig.");
//            }
//        });
//
//        builder.show();
//    }


    public void withRatingBar(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setTitle("Bewertung");
        View dialogLayout = inflater.inflate(R.layout.dialog_rating, null);
        final RatingBar ratingBar1 = dialogLayout.findViewById(R.id.ratingBar1);
        final RatingBar ratingBar2 = dialogLayout.findViewById(R.id.ratingBar2);
        final Integer articleId = getIntent().getIntExtra("article_id", -1);
        builder.setView(dialogLayout);
        builder.setPositiveButton("Bestätigen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Bewertung 1: " + ratingBar1.getRating() +
                        "\n Bewertung 2: " + ratingBar2.getRating() + "\n Artikel ID:" + articleId, Toast.LENGTH_SHORT).show();

                // Rating in Datenbank reinschreiben
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getResources().getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                //Todo WIP
                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                Rating rating = new Rating();
                rating.setContentId(articleId);
                rating.setCredibility((int) ratingBar1.getRating());
                rating.setNeutrality((int) ratingBar2.getRating());
                RatingPojo ratingPojo = new RatingPojo();
                ratingPojo.setRating(rating);
                // Return type void
                apiCallData(jsonPlaceHolderApi, ratingPojo);
            }
        });

        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Info", "Bewertung abgebrochen");
            }
        });
        builder.show();
    }

    public void apiCallData(JsonPlaceHolderApi jsonPlaceHolderApi, RatingPojo ratingPojo){

        Call<Void> call = jsonPlaceHolderApi.getRating(ratingPojo);
        // Cant run this on the UI Thread, Retrofit runs it for us on a background thread
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG,"apiCallData return Code != 200");
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG,"apiCallData: ", t);
            }
        });

    }
/*    public void onClickSubmitButton(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_rating, null);
        RatingBar ratingBar1 = dialogLayout.findViewById(R.id.ratingBar1);
        RatingBar ratingBar2 = dialogLayout.findViewById(R.id.ratingBar2);
        Integer articleId = getIntent().getIntExtra("article_id", -1);
        Toast.makeText(getApplicationContext(), "Bewertung 1: " + ratingBar1.getRating() + "\n Bewertung 2:" + ratingBar2.getRating(), Toast.LENGTH_SHORT).show();
    }*/
}
