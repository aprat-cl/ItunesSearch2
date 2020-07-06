package com.myapp.itunessearch2.data.api;

import com.myapp.itunessearch2.data.api.Responses.Itunes_SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface itunes_service_interface {
    @GET("search")
    Call<Itunes_SearchResponse> getSearch(
            @Query("term") String term,
            @Query("mediaType") String mediaType,
            @Query("limit") String limit
    );
}
