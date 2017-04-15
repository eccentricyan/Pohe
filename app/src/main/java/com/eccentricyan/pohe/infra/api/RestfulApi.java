package com.eccentricyan.pohe.infra.api;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shiyanhui on 2017/04/15.
 */
public interface RestfulApi {
    @GET("{category}/list")
    Observable<JsonObject> articles(@Path("category")String category,
                                @Nullable @Query("offset") int offset);
    @GET("top/params")
    Single<JsonObject> categories();
}
