package com.example.couscousapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("contents")
    Call<List<Content>> getContents();

    @GET("data")
    Call<List<Data>> getData();

}
