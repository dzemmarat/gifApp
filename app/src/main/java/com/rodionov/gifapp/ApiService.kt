package com.rodionov.gifapp

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by rodionov on 18.11.2019.
 */
interface ApiService {

    @GET("/v1/gifs/random")
    fun getRandom(@Query("api_key") apiKey:String): Observable<RandomResponse>
}