package com.example.comppress.api;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.comppress.activity.MainActivity;
import com.example.comppress.json_model.Feed;
import com.example.comppress.json_model.Rating;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;



public class ApiRepository {

    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    public ApiRepository (String baseUrl){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    public void apiCallGetNews(final ProgressBar progressBar, final RecyclerView.Adapter adapter, final List<Feed> feedList, String apiCallString) {
        Call<List<Feed>> call = null;
        if /*(apiCallString.equals("ratedNews")) {
            call = jsonPlaceHolderApi.ratedNews();
            Log.i("Info", "BestRated");
        } else if (apiCallString.equals("latestNews")) {
            call = jsonPlaceHolderApi.latestNews();
            Log.i("Info", "Latest");
        } else if */ (apiCallString.equals("day")){
            call = jsonPlaceHolderApi.getNewsOfDay();
            Log.i("Info", "Day");
        } else if (apiCallString.equals("week")){
            call = jsonPlaceHolderApi.getNewsOfWeek();
            Log.i("Info", "Week");
        } else if (apiCallString.equals("month")){
            call = jsonPlaceHolderApi.getNewsOfMonth();
            Log.i("Info", "Month");
        }
        // Cant run this on the UI Thread, Retrofit runs it for us on a background thread
        assert call != null;
        call.enqueue(new Callback<List<Feed>>() {

            @Override
            public void onResponse(Call<List<Feed>> call, Response<List<Feed>> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "apiCallData return Code != 200");
                    return;
                }

                progressBar.setVisibility(View.GONE);

                List<Feed> responseFeedList = response.body();

                assert responseFeedList != null;
                feedList.addAll(responseFeedList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Feed>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "apiCallData: ", t);
            }
        });
    }

    public void apiCallContent(Rating rating){

        Call<Void> call = jsonPlaceHolderApi.getRating(rating);
        // Cant run this on the UI Thread, Retrofit runs it for us on a background thread
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG,"apiCallData return Code != 200");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG,"apiCallData: ", t);
            }
        });

    }

    public void apiCallUserReference(String userReference){

        Call<Long> call = jsonPlaceHolderApi.sendUserReference(userReference);
        // Cant run this on the UI Thread, Retrofit runs it for us on a background thread
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG,"apiCallData return Code != 200");
                    return;
                }
                MainActivity.personId = response.body();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e(TAG,"apiCallData: ", t);
            }
        });

    }
}
