package com.example.comppress.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.comppress.R;
import com.example.comppress.adapter.CustomExpandableListAdapter;
import com.example.comppress.api.ApiRepository;
import com.example.comppress.json_model.Rating;
import com.example.comppress.views.ExpandableListDataPump;
import com.example.comppress.views.NestedWebView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

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

        NestedWebView webView = findViewById(R.id.reader_webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            //Enables Whatsapp and Mail Share-Buttons on sites in WebView
            @Override
            public boolean shouldOverrideUrlLoading(WebView wv, String url) {
                if(url.startsWith("mailto:") || url.startsWith("whatsapp:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    wv.goBack();
                    return true;
                }
                return false;
                }
                                 });
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.comment:
                // User chose the "Comment" item, show the app settings UI...
                Log.i("Info", "Button 'Comment' pressed");
                return true;*/

            case R.id.favorite:
                Toast.makeText(getApplicationContext(),"Zu Favoriten hinzufügen (In Planung)", Toast.LENGTH_SHORT).show();
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
                .setPositiveButton(R.string.rate, null) //Set to null. We override the onclick
                .setNegativeButton(android.R.string.cancel, null)
                .create();



        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextSize(18);
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

                        rating.setCredibility(myAdapter.getRatingPosition(0,0));
                        rating.setNeutrality(myAdapter.getRatingPosition(1,0));
                        rating.setInformativity(myAdapter.getRatingPosition(2,0));

                        rating.setPersonId(MainActivity.personId);

                        //RatingPojo ratingPojo = new RatingPojo();
                        //ratingPojo.setRating(rating);
                        //ratingPojo.setUserReference(MainActivity.userReference);
                        //ratingPojo.getRating().setPersonId(MainActivity.personId);


                        boolean allRatingsTicked = true;

                        for(int i = 0; i < 3; i++){
                            if(myAdapter.getRatingPosition(i,0)==0) allRatingsTicked = false;
                        }

                        if (allRatingsTicked){
                            final ApiRepository apiRepository = new ApiRepository(getResources().getString(R.string.base_url));
                            apiRepository.apiCallContent(rating);
                            Toast.makeText(getApplicationContext(),"Bewertung erfolgreich", Toast.LENGTH_SHORT).show();
                            //TODO: Intent for successful rating
                            dialog.dismiss();
                        } else {
                        Toast.makeText(getApplicationContext(),"Bitte bewerten Sie alle Kriterien", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
            }
        });
        dialog.show();
    }
}
