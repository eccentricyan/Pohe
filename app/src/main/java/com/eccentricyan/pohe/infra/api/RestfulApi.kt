package com.eccentricyan.pohe.infra.api

import com.google.gson.JsonObject

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by shiyanhui on 2017/04/15.
 */
interface RestfulApi {
    @GET("{category}/list")
    fun articles(@Path("category") category: String,
                 @Query("offset") offset: Int): Observable<JsonObject>

    @GET("top/params")
    fun categories(): Single<JsonObject>
}
