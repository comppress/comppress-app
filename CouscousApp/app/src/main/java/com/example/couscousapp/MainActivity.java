package com.example.couscousapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButton(View view){
        // http://arnab.ch/blog/2013/08/asynchronous-http-requests-in-android-using-volley/
        // http://46.101.203.225:8080/demo/getNewsCategory

        String jsonInput = "{\"numberNews\": 10,\"category\": \"\"}";
        final String URL = "/volley/resource/12";
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", "AbCdEfGh123456");

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        MainActivity.getInstance().addToRequestQueue(req);

        Log.i("info",jsonInput);

    }

}
