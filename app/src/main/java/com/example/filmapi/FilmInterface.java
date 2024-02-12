package com.example.filmapi;


import com.example.filmapi.models.FilmInfo;
import com.example.filmapi.models.Root;
import com.example.filmapi.models.Trailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface FilmInterface {

    @Headers({"X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b", "Content-Type: application/json"})
    @GET("films/top?type=TOP_100_POPULAR_FILMS&page=1")
    Call<Root> getPageId();

    @Headers({"X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b", "Content-Type: application/json"})
    @GET("films/top?type=TOP_100_POPULAR_FILMS&page=1")
    Call<Root> getPagesId();

    @Headers({"X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"})
    @GET("films/{kinopiskId}")
    Call<FilmInfo> getFid(@Path("kinopiskId") int kinopoiskId);

    @Headers({"X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"})
    @GET("films/{kinopiskId}/videos")
    Call<Trailer> getVId(@Path("kinopiskId") int kinopoiskId);
}
