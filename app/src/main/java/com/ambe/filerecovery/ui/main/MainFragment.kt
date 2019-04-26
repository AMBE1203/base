package com.ambe.filerecovery.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.databinding.adapters.ListenerUtil.getListener
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.ambe.filerecovery.R
import com.ambe.filerecovery.databinding.FragmentMainBinding
import com.ambe.filerecovery.ui.BaseFragment
import com.ambe.filerecovery.utils.Constants


class MainFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding
    private val mainHandler = MainHandler()
    private val TAG = MainFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate<FragmentMainBinding>(
                inflater,
                R.layout.fragment_main,
                container,
                false
            ).apply {
                handler = mainHandler
            }

        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

        private const val REQUEST_CODE_PERMISSION_READ = 12

    }

    inner class MainHandler : BaseHandler() {

        fun onClickSetting() {
            navController.navigate(R.id.action_mainFragment_to_settingFragment)
        }

        fun onClickImage() {
            if (checkWritePermission()) {
                val bundle = Bundle()
                bundle.putString("type", Constants.IMAGES)
                navController.navigate(R.id.action_mainFragment_to_layoutsFragment, bundle)
            }
        }

        fun onClickVideo() {
            if (checkWritePermission()) {
                val bundle = Bundle()
                bundle.putString("type", Constants.VIDEOS)
                navController.navigate(R.id.action_mainFragment_to_layoutsFragment, bundle)
            }
        }

        fun onClickSound() {
            if (checkWritePermission()) {
                val bundle = Bundle()
                bundle.putString("type", Constants.SOUNDS)
                navController.navigate(R.id.action_mainFragment_to_layoutsFragment, bundle)
            }
        }

        fun onClickDocument() {
            if (checkWritePermission()) {
                val bundle = Bundle()
                bundle.putString("type", Constants.DOCUMENT)
                navController.navigate(R.id.action_mainFragment_to_layoutsFragment, bundle)
            }
        }

        fun onClickApp() {
            if (checkWritePermission()) {
                val bundle = Bundle()
                bundle.putString("type", Constants.APP)
                navController.navigate(R.id.action_mainFragment_to_layoutsFragment, bundle)
            }
        }

        fun onClickOther() {
            if (checkWritePermission()) {
                val bundle = Bundle()
                bundle.putString("type", Constants.OTHER)
                navController.navigate(R.id.action_mainFragment_to_layoutsFragment, bundle)
            }
        }
    }

    private fun checkWritePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    context!!, Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), REQUEST_CODE_PERMISSION_READ
                )
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION_READ) {

            if (!(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(
                    context,
                    "You need agree this permission to using it !",
                    Toast.LENGTH_SHORT
                ).show()
                openScreenPermission()
            } else {
                //TODO
            }

        }
    }

    private fun openScreenPermission() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        startActivity(intent)
    }


}
