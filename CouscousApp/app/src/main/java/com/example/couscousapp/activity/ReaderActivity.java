package com.example.couscousapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
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
import android.widget.Button;
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReaderActivity extends AppCompatActivity {

    private static final String TAG = "ReaderActivity";
    ExpandableListView myList;
    CustomExpandableListAdapter myAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<String, List<String>> expandableListDetail;


    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);


        Toolbar tbReader = findViewById(R.id.tb_reader);
        setSupportActionBar(tbReader);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        tbReader.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();}
        });

        WebView webView = findViewById(R.id.reader_webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(Objects.requireNonNull(getIntent().getStringExtra("external_url")));

        //Extended Floating Action Button
        ExtendedFloatingActionButton efab = findViewById(R.id.floating_action_button);
        float temp = getIntent().getFloatExtra("article_rating", -1);
        if (temp != -1) {
            efab.setText(Float.toString(temp).substring(0,3));
            efab.setTextSize(20);
        }
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
            /*case R.id.comment:
                // User chose the "Comment" item, show the app settings UI...
                Log.i("Info", "Button 'Comment' pressed");
                return true;*/

            case R.id.favorite:
                Toast.makeText(getApplicationContext(),"Zu Favoriten hinzufügen (Soon™)", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.browser:
                String url = getIntent().getStringExtra("external_url");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void expandableRating(View view) {
        final Integer articleId = getIntent().getIntExtra("article_id", -1);

        myList = new ExpandableListView(this);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        myAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        myList.setAdapter(myAdapter);
        myList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        myList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
/*                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).
                                get(childPosition), Toast.LENGTH_SHORT
                        ).show();*/
                return false;
            }
        });
        myList.setAdapter(myAdapter);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(myList)
                .setTitle("Bewertung")
                .setPositiveButton(android.R.string.ok, null) //Set to null. We override the onclick
                .setNegativeButton(android.R.string.cancel, null)
                .create();



        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextSize(20.0f);
                positiveButton.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));

                positiveButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        if (MainActivity.personId == null) {
                            Toast.makeText(getApplicationContext(),"Keine Id gefunden", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Rating rating = new Rating();
                        rating.setContentId(articleId);
                        // Top 3
                        rating.setCredibility(myAdapter.getRatingPosition(0,0));
                        rating.setNeutrality(myAdapter.getRatingPosition(1,0));
                        rating.setInformativity(myAdapter.getRatingPosition(2,0));
                        // Sub
                        /*rating.setFactuality((int) myAdapter.getRatingPosition(0,1));
                        rating.setSourceTransparency((int) myAdapter.getRatingPosition(0,2));
                        rating.setPluralityOfViews((int) myAdapter.getRatingPosition(0,3));
                        // Sub 2
                        rating.setImpartiality((int) myAdapter.getRatingPosition(1,1));
                        rating.setDispassion((int) myAdapter.getRatingPosition(1,2));
                        rating.setXX((int) myAdapter.getRatingPosition(1,3));
                        // Sub 3
                        rating.setXX((int) myAdapter.getRatingPosition(2,1));
                        rating.setXX((int) myAdapter.getRatingPosition(2,2));
                        rating.setXX((int) myAdapter.getRatingPosition(2,3));*/

                        RatingPojo ratingPojo = new RatingPojo();
                        ratingPojo.setRating(rating);
                        ratingPojo.setUserReference(MainActivity.userReference);
                        ratingPojo.getRating().setPersonId(MainActivity.personId);
                        // Return type void

                        boolean allRatingsTicked = true;

                        for(int i = 0; i < 3; i++){
                            if(myAdapter.getRatingPosition(i,0)==0) allRatingsTicked = false;
                        }

                        if (allRatingsTicked){
                            final ApiRepository apiRepository = new ApiRepository(getResources().getString(R.string.base_url));
                            apiRepository.apiCallContent(ratingPojo);
                            Toast.makeText(getApplicationContext(),"Bewertung erfolgreich", Toast.LENGTH_SHORT).show();
                            //TODO: Intent for successful rating
                            dialog.dismiss();
                        } else {
                        Toast.makeText(getApplicationContext(),"Bitte bewerte alle Kriterien", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
            }
        });
        dialog.show();
    }
}
