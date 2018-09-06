package com.eccentricyan.pohe.common.migration

import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Created by shiyanhui on 2017/04/15.
 */

class Migration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {

        // For now anyone migration is needed!
    }

    // https://github.com/realm/realm-java/issues/1919#issuecomment-165375852
    override fun equals(o: Any?): Boolean {
        return o is Migration
    }
}

