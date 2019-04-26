package com.ambe.filerecovery.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ambe.filerecovery.R
import com.ambe.filerecovery.interfaces.IOnBackListener
import com.ambe.filerecovery.interfaces.IOnFragmentInteractListener
import com.ambe.filerecovery.ui.dialog.LoadingDialog

/**
 *  Created by AMBE on 10/4/2019 at 8:46 AM.
 */
abstract class BaseFragment : Fragment() {
    protected var listenerI: IOnFragmentInteractListener? = null
    private var loadingDialog: LoadingDialog? = null
    protected lateinit var navController: NavController

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IOnFragmentInteractListener) {
            listenerI = context
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(context as Activity, R.id.nav_host_fragment)

    }

    override fun onDetach() {
        super.onDetach()
        listenerI = null
    }

    fun showLoadingDialog() {
        if (loadingDialog == null) {
            context?.let {
                loadingDialog = LoadingDialog(it)
                loadingDialog?.setCanceledOnTouchOutside(false)

            }
        }

        loadingDialog?.show()
    }

    fun hideLoadingDialog() {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
    }

    open inner class BaseHandler : IOnBackListener {
        override fun onBack() {
            listenerI?.onBackToHome()
        }

    }

}