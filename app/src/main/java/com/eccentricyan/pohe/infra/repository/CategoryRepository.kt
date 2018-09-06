package com.eccentricyan.pohe.infra.repository

import com.eccentricyan.pohe.di.scope.AllOpen
import com.eccentricyan.pohe.infra.api.RestfulApi
import com.eccentricyan.pohe.domain.entity.Category
import com.google.gson.Gson

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Single

/**
 * Created by shiyanhui on 2017/04/15.
 */

@Singleton
@AllOpen
class CategoryRepository {
    @JvmField
    @Inject
    var api: RestfulApi? = null
    @JvmField
    @Inject
    var gson: Gson? = null

    fun categories(): Single<Category> {
        return api!!.categories()
                .map { jsonObject -> gson!!.fromJson(jsonObject.get("category"), Category::class.java) }
    }
}
