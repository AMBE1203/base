package com.ambe.filerecovery.interfaces

import android.support.v4.app.Fragment

/**
 *  Created by AMBE on 10/4/2019 at 8:51 AM.
 */
interface IOnFragmentInteractListener {

    fun onBackToHome()

    fun onOpenFragment(fragment: Fragment)
}