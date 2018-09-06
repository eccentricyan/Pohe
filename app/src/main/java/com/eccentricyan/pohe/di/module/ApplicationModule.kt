package com.eccentricyan.pohe.di.module

import android.util.Log

import com.eccentricyan.pohe.Application
import com.eccentricyan.pohe.common.utils.NetUtils
import com.eccentricyan.pohe.defines.Defines
import com.eccentricyan.pohe.di.scope.ApplicationScope
import com.eccentricyan.pohe.infra.api.RestfulApi
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import org.greenrobot.eventbus.EventBus

import java.io.IOException
import java.util.concurrent.TimeUnit

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import com.eccentricyan.pohe.defines.Defines.CACHE
import com.eccentricyan.pohe.defines.Defines.SITE_URL

@Module
class ApplicationModule {

    val isOnline: Boolean?
        @Provides
        get() = NetUtils.checkNet(Application.instance!!)

    @Provides
    @ApplicationScope
    fun realm(): Realm {
        return Realm.getDefaultInstance()
    }

    @Provides
    @ApplicationScope
    fun restfulApi(): RestfulApi {
        return restApi(okHttpclient())
    }

    fun restApi(okHttpClient: OkHttpClient): RestfulApi {
        synchronized(RestfulApi::class.java) {
            val retrofit = Retrofit.Builder()
                    .baseUrl(SITE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(RestfulApi::class.java)
        }
    }

    protected fun okHttpclient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder()
            request.removeHeader("Pragma")
            if (isOnline!!) {
                request.addHeader("cache-control", "public, max-age=" + 60)
            } else {
                Log.e("offline", "offline")
                request.header("cache-control", "public, only-if-cached, max-stale=" + 24 * 60 * 60)
            }
            chain.proceed(request.build())
        }
        httpClient.writeTimeout((15 * 60 * 1000).toLong(), TimeUnit.MILLISECONDS)
        httpClient.readTimeout((60 * 1000).toLong(), TimeUnit.MILLISECONDS)
        httpClient.connectTimeout((20 * 1000).toLong(), TimeUnit.MILLISECONDS)

        return httpClient.cache(CACHE).build()
    }

    @Provides
    @ApplicationScope
    fun eventBus(): EventBus {
        return EventBus.getDefault()
    }

    @Provides
    @ApplicationScope
    fun subscribeScheduler(): Scheduler {
        return Schedulers.io()
    }


    @Provides
    @ApplicationScope
    fun gson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    companion object {
    }

}