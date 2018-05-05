package com.tanavota.tanavota

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tanavota.tanavota.view.Navigator
import com.tanavota.tanavota.view.home.HomeFragment

class MainActivity : AppCompatActivity(), Navigator {
    override val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigateToFragment(HomeFragment.newInstance())
        }
    }

}
