package com.ambe.filerecovery.ui.setting

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.ambe.filerecovery.R
import com.ambe.filerecovery.databinding.FragmentSettingBinding
import com.ambe.filerecovery.ui.BaseFragment
import com.ambe.filerecovery.ui.dialog.AutoClearDialog
import com.ambe.filerecovery.ui.dialog.ChangePassDialog
import com.ambe.filerecovery.ui.dialog.ConfirmDialog
import com.ambe.filerecovery.ui.dialog.SetYourPassDialog
import com.ambe.filerecovery.utils.Constants
import com.ambe.filerecovery.utils.PrefUtils


class SettingFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingBinding
    private var pref: PrefUtils? = null
    private val settingHandler = SettingHandler()
    private val TAG = SettingFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate<FragmentSettingBinding>(
            inflater,
            R.layout.fragment_setting,
            container,
            false
        ).apply {
            handler = settingHandler
        }
        pref = PrefUtils.newInstance(context!!)


        binding.textClear.text = "Auto Clear (${pref?.getString(
            Constants.AUTO_CLEAR,
            context!!.getString(R.string._30_days)
        )})"

        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }

    inner class SettingHandler : BaseHandler() {

        fun onClickBack() {
            navController.navigateUp()
        }

        fun onClickReset() {
            var confirmDialog = ConfirmDialog(context!!)
            confirmDialog.setListener(object : ConfirmDialog.IOnConfirmDialogListener {
                override fun onCancel() {
                    confirmDialog.dismiss()
                }

                override fun onAgree(pass: String) {

                    Log.e(TAG, pass)

                    confirmDialog.dismiss()
                }
            })
            confirmDialog.show()

        }

        fun onClickChangeAccountPass() {

            var changePassDialog = ChangePassDialog(context!!)

            changePassDialog.setListener(object : ChangePassDialog.IOnChangePassDialog {
                override fun onCancel() {
                    changePassDialog.dismiss()
                }

                override fun onAgree(str: String) {

                    pref?.putString(Constants.PIN, str)
                    changePassDialog.dismiss()
                }
            })

            changePassDialog.show()

        }

        fun onClickAutoClear() {
            val autoClearDialog = AutoClearDialog(context!!)
            autoClearDialog.setListener(object : AutoClearDialog.IOnAutoClearDialogListener {
                override fun onCancel() {
                    autoClearDialog.dismiss()
                }

                override fun onAgree(str: String) {

                    pref?.putString(Constants.AUTO_CLEAR, str)
                    binding.textClear.text = "Auto Clear ($str)"


                    autoClearDialog.dismiss()
                }
            })
            autoClearDialog.show()
        }


    }
}
