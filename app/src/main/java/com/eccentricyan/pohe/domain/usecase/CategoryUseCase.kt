package com.eccentricyan.pohe.domain.usecase

import com.eccentricyan.pohe.infra.repository.CategoryRepository

import javax.inject.Inject

import io.realm.Realm

/**
 * Created by shiyanhui on 2017/04/15.
 */

class CategoryUseCase {
    @JvmField
    @Inject
    internal var repository: CategoryRepository? = null
    @JvmField
    @Inject
    internal var realm: Realm? = null
}
