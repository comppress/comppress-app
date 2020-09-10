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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.couscousapp.R;
import com.example.couscousapp.adapter.CustomExpandableListAdapter;
import com.example.couscousapp.api.ApiRepository;
import com.example.couscousapp.api.JsonPlaceHolderApi;
import com.example.couscousapp.json_model.Data;
import com.example.couscousapp.json_model.Rating;
import com.example.couscousapp.json_model.RatingPojo;
import com.example.couscousapp.views.ExpandableListDataPump;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReaderActivity extends AppCompatActivity {

    private static final String TAG = "ReaderActivity";
    ExpandableListView myList;
    ExpandableListAdapter myAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;


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

    public void withRatingBar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setTitle("Bewertung");
        View dialogLayout = inflater.inflate(R.layout.dialog_rating, null);
        final RatingBar ratingBar1 = dialogLayout.findViewById(R.id.ratingBar1);
        final RatingBar ratingBar2 = dialogLayout.findViewById(R.id.ratingBar2);
        final RatingBar ratingBar3 = dialogLayout.findViewById(R.id.ratingBar3);
        final Integer articleId = getIntent().getIntExtra("article_id", -1);
        builder.setView(dialogLayout);
        builder.setPositiveButton("Bestätigen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Bewertung 1: " + ratingBar1.getRating() +
                        "\n Bewertung 2: " + ratingBar2.getRating() +
                        "\n Bewertung 3: " + ratingBar3.getRating() + "\n Artikel ID:" + articleId, Toast.LENGTH_SHORT).show();

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
                rating.setInformativity((int) ratingBar3.getRating());
                RatingPojo ratingPojo = new RatingPojo();
                ratingPojo.setRating(rating);
                // Return type void
                final ApiRepository apiRepository = new ApiRepository(getResources().getString(R.string.base_url));
                apiRepository.apiCallContent(jsonPlaceHolderApi, ratingPojo);
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

    public void expandableRating(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bewertung");
        myList = new ExpandableListView(this);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        myAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        myList.setAdapter(myAdapter);
        myList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        myList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
        myList.setAdapter(myAdapter);
        builder.setView(myList);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
