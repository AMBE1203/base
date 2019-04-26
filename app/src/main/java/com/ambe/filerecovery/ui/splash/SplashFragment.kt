package com.ambe.filerecovery.ui.splash


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ambe.filerecovery.R
import com.ambe.filerecovery.ui.BaseFragment


class SplashFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({

            navController.navigate(R.id.action_splashFragment_to_passcodeFragment)

        }, getSplashScreenDuration())
    }

    override fun onResume() {
        super.onResume()
//        Handler().postDelayed({
//
//            navController.navigate(R.id.action_splashFragment_to_passcodeFragment)
//
//        }, getSplashScreenDuration())
    }

    private fun getSplashScreenDuration() = 1500L

}
