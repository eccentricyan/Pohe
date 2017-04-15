package com.eccentricyan.pohe.domain.entity;

import org.parceler.Parcel;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by shiyanhui on 2017/04/15.
 */

@Parcel
public class Category {
    public String name;
    public String description;
    public String[] categories;
    public Category() {

    }

}
