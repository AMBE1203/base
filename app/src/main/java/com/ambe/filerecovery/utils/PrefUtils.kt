package com.ambe.filerecovery.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import android.view.View
import android.view.WindowManager

/**
 *  Created by AMBE on 10/4/2019 at 9:15 AM.
 */
class PrefUtils {

    private var pref: SharedPreferences? = null

    private constructor(context: Context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
    }

    companion object {

        private var instance: PrefUtils? = null

        fun newInstance(context: Context): PrefUtils {
            if (instance == null) {
                instance = PrefUtils(context.applicationContext)
            }

            return instance!!
        }
    }

    fun putBoolean(key: String, value: Boolean) {
        pref?.apply {
            val editor = this.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean? {
        return pref?.getBoolean(key, defaultValue)
    }

    fun putString(key: String, value: String) {
        pref?.apply {
            val editor = this.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }

    fun getString(key: String, defaultValue: String): String? {
        return pref?.getString(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        pref?.apply {
            val editor = this.edit()
            editor.putInt(key, value)
            editor.apply()
        }
    }

    fun getInt(key: String, defaultValue: Int): Int? {
        return pref?.getInt(key, defaultValue)
    }

    fun setStatusBarColor(activity:Activity,color: Int, state: StatusBarState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            var newUiVisibility = activity.window.decorView.systemUiVisibility
            if (state === StatusBarState.Light) {
                //Dark Text to show up on your light status bar
                newUiVisibility = newUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else if (state === StatusBarState.Dark) {
                //Light Text to show up on your dark status bar
                newUiVisibility = newUiVisibility and (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR).inv()
            }
            activity.window.decorView.systemUiVisibility = newUiVisibility
            activity.window.statusBarColor = color
        }
    }
    enum class StatusBarState {
        Light,
        Dark
    }


}