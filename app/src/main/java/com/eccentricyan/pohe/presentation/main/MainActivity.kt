package com.eccentricyan.pohe.presentation.main

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.crashlytics.android.Crashlytics
import com.eccentricyan.pohe.R
import com.eccentricyan.pohe.presentation.base.BaseActivity

import io.fabric.sdk.android.Fabric

class MainActivity : BaseActivity(), MainFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolBar()
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commit()
        }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
