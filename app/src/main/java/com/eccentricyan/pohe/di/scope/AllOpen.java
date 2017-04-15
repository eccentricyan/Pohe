package com.eccentricyan.pohe.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

/**
 * Created by shiyanhui on 2017/04/15.
 */

@Scope
@Retention(RetentionPolicy.SOURCE)
public @interface AllOpen {
}