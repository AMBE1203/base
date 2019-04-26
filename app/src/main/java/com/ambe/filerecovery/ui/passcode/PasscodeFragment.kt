package com.ambe.filerecovery.ui.passcode


import android.animation.ObjectAnimator
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.ambe.filerecovery.R
import com.ambe.filerecovery.databinding.FragmentPasscodeBinding
import com.ambe.filerecovery.ui.BaseFragment
import com.ambe.filerecovery.ui.dialog.NotiDialog
import com.ambe.filerecovery.ui.view.pinlockview.IndicatorDots
import com.ambe.filerecovery.ui.view.pinlockview.PinLockListener
import com.ambe.filerecovery.utils.Constants
import com.ambe.filerecovery.utils.PrefUtils
import com.ambe.filerecovery.utils.Utils


class PasscodeFragment : BaseFragment() {

    private lateinit var binding: FragmentPasscodeBinding
    private val passcodeHandler = PasscodeHandler()
    private val TAG = PasscodeFragment::class.java.simpleName
    private var mSetPin: Boolean = false
    private var pref: PrefUtils? = null
    private var mFirstPin = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentPasscodeBinding>(
            inflater,
            R.layout.fragment_passcode,
            container,
            false
        ).apply {
            handler = passcodeHandler
        }
        pref = PrefUtils.newInstance(context!!)
        pref?.putString(Constants.PIN, Utils.sha256("1234"))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pinlockView.attachIndicatorDots(binding.indicatorDots)
        binding.pinlockView.pinLength = PIN_LENGTH


        binding.pinlockView.setPinLockListener(object : PinLockListener {
            override fun onEmpty() {
                Log.e(TAG, "Pin empty")
            }

            override fun onComplete(pin: String?) {
                if (mSetPin) {
                    setPin(pin)

                } else {
                    checkPin(pin)
                }
            }

            override fun onPinChange(pinLength: Int, intermediatePin: String?) {
                Log.e(
                    TAG,
                    "Pin changed, new length $pinLength with intermediate pin $intermediatePin"
                )
            }
        })

    }

    private fun checkPin(pin: String?) {
        if (Utils.sha256(pin!!) == getPinFromSharedPreferences()) {
            setResult(1)

        } else {

            shake()
            showDialogPassWrong()
            binding.pinlockView.resetPinLockView()
        }
    }

    private fun shake() {
        val objectAnimator = ObjectAnimator.ofFloat(
            binding.pinlockView, View.TRANSLATION_X,
            0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f
        ).setDuration(1000)
        objectAnimator.start()
    }

    private fun showDialogPassWrong() {

        var notiDialog = NotiDialog(context!!)
        notiDialog.show()
    }

    private fun getPinFromSharedPreferences(): String? {
        return pref?.getString(Constants.PIN, "")
    }

    private fun writePinToSharedPreferences(pin: String?) {
        pref?.putString(Constants.PIN, Utils.sha256(pin!!))
    }

    private fun setResult(i: Int) {
        if (i == 1) {
            navController.navigate(R.id.action_passcodeFragment_to_mainFragment)
        } else {

        }
    }

    private fun setPin(pin: String?) {
        if (mFirstPin == "") {
            mFirstPin = pin!!
            binding.pinlockView.resetPinLockView()
        } else {
            if (pin == mFirstPin) {
                writePinToSharedPreferences(pin)
                setResult(1)
            } else {
                shake()
                Toast.makeText(context, getString(R.string.pinlock_tryagain), Toast.LENGTH_LONG)
                    .show()
                binding.pinlockView.resetPinLockView()
                mFirstPin = ""
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PasscodeFragment()

        private const val PIN_LENGTH = 4
    }

    inner class PasscodeHandler : BaseHandler() {

        fun onClickForgotPass() {
            navController.navigate(R.id.action_passcodeFragment_to_mainFragment)
        }

    }


}
