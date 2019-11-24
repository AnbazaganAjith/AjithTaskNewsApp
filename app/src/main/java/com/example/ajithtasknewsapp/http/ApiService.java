package com.example.ajithtasknewsapp.http;

import com.example.ajithtasknewsapp.Dao.News;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by dw158 on 10/17/2016 AD.
 */

public interface ApiService {


    @GET("top-headlines")
    Observable<News> getNewsData(
            @Query("sources") String source,
            @Query("apiKey") String apikey);

}
