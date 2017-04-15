package com.eccentricyan.pohe.presentation.main;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.eccentricyan.pohe.R;
import com.eccentricyan.pohe.presentation.base.BaseActivity;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity implements MainFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,MainFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
