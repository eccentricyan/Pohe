package com.eccentricyan.pohe;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.eccentricyan.pohe.common.migration.Migration;
import com.facebook.stetho.Stetho;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.eccentricyan.pohe.defines.Defines.CONSUMER_KEY;
import static com.eccentricyan.pohe.defines.Defines.CONSUMER_SECRET;
import static com.eccentricyan.pohe.defines.Defines.DB_VERSION;

public class MyApplication extends Application {
    private static MyApplication instance = null;
//    private AppComponent appComponent;

    public void onCreate() {
        super.onCreate();
        instance = this;
        // Realm設定ここから
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .migration(new Migration())
                .schemaVersion(DB_VERSION)
                .build();

        Realm.setDefaultConfiguration(config);
        // Realm設定ここまで

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
        initTwitterClient();
        initCrashlytics();
    }

    private void initTwitterClient() {
        final TwitterAuthConfig authConfig = new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }

    private void initCrashlytics() {
        // Set up Crashlytics, disabled for debug builds
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(this, crashlyticsKit, new Crashlytics());
    }

    public static MyApplication getInstance() {
        return instance;
    }
}