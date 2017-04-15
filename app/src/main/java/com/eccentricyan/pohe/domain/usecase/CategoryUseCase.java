package com.eccentricyan.pohe.domain.usecase;

import com.eccentricyan.pohe.infra.repository.CategoryRepository;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class CategoryUseCase {
    @Inject
    CategoryRepository repository;
    @Inject
    Realm realm;
}
