package com.eccentricyan.pohe

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.eccentricyan.pohe.common.migration.Migration
import com.eccentricyan.pohe.defines.Defines
import com.facebook.stetho.Stetho
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.uphyca.stetho_realm.RealmInspectorModulesProvider

import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {
    //    private AppComponent appComponent;

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Realm設定ここから
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .migration(Migration())
                .schemaVersion(Defines.DB_VERSION.toLong())
                .build()

        Realm.setDefaultConfiguration(config)
        // Realm設定ここまで

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build())
        initTwitterClient()
        initCrashlytics()
    }

    private fun initTwitterClient() {
        val authConfig = TwitterAuthConfig(Defines.CONSUMER_KEY, Defines.CONSUMER_SECRET)
        Fabric.with(this, Twitter(authConfig))
    }

    private fun initCrashlytics() {
        // Set up Crashlytics, disabled for debug builds
        val crashlyticsKit = Crashlytics.Builder()
                .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build()

        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(this, crashlyticsKit, Crashlytics())
    }

    companion object {
        var instance: MyApplication? = null
            private set
    }
}