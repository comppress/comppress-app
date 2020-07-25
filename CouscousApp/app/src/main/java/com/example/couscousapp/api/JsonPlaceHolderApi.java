package com.example.couscousapp.api;

import com.example.couscousapp.json_model.Content;
import com.example.couscousapp.json_model.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("contents")
    Call<List<Content>> getContents();

    @GET("data?listLength=50")
    Call<List<Data>> getData();

    // Todo: @POST

}
