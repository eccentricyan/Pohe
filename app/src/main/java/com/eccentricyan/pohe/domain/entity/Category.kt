package com.eccentricyan.pohe.domain.entity

import org.parceler.Parcel

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by shiyanhui on 2017/04/15.
 */

@Parcel
class Category {
    var name: String? = null
    var description: String? = null
    var categories: Array<String>? = null

}
