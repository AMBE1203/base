package com.ambe.filerecovery.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.navigation.Navigation
import com.ambe.filerecovery.R
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.os.Build
import android.util.Log


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
    }



}
