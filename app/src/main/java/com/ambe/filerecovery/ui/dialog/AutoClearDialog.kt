package com.ambe.filerecovery.ui.dialog

import android.content.Context
import android.widget.CheckBox
import com.ambe.filerecovery.R
import com.ambe.filerecovery.interfaces.IOnSimpleDialogListener
import com.ambe.filerecovery.ui.BaseDialog
import com.ambe.filerecovery.utils.Constants
import com.ambe.filerecovery.utils.PrefUtils
import com.ambe.filerecovery.utils.Utils
import kotlinx.android.synthetic.main.dialog_auto_clear.*

/**
 *  Created by AMBE on 12/4/2019 at 8:29 AM.
 */
class AutoClearDialog(context: Context) : BaseDialog(context) {
    override fun setAnimation() {
        Utils.animateDialog(vgr_dialog_auto_clear)
    }

    private var listener: IOnAutoClearDialogListener? = null
    private var pref: PrefUtils? = null


    override fun initView() {
        pref = PrefUtils.newInstance(context)
        var str = pref?.getString(Constants.AUTO_CLEAR, context.getString(R.string._30_days))

        when (str) {
            context.getString(R.string._7_days) -> setCbIsChecked(cb_07, cb_30, cb_90)
            context.getString(R.string._30_days) -> setCbIsChecked(cb_30, cb_90, cb_07)
            else -> setCbIsChecked(cb_90, cb_07, cb_30)
        }


        rll_07.setOnClickListener {
            setCbIsChecked(cb_07, cb_30, cb_90)
        }

        rll_30.setOnClickListener {
            setCbIsChecked(cb_30, cb_90, cb_07)
        }

        rll_90.setOnClickListener {
            setCbIsChecked(cb_90, cb_07, cb_30)
        }

        lnl_btn_auto_clear_accept.setOnClickListener { listener?.onAgree(resultCheckbox()) }
        lnl_btn_auto_clear_cancel.setOnClickListener { listener?.onCancel() }
    }

    private fun resultCheckbox(): String {
        return when {
            cb_07.isChecked -> context.getString(R.string._7_days)
            cb_30.isChecked -> context.getString(R.string._30_days)
            else -> context.getString(R.string._90_days)
        }
    }

    private fun setCbIsChecked(cb1: CheckBox, cb2: CheckBox, cb3: CheckBox) {
        cb1.isChecked = true
        cb2.isChecked = false
        cb3.isChecked = false
    }

    override fun getLayout(): Int {
        return R.layout.dialog_auto_clear
    }

    fun setListener(listener: IOnAutoClearDialogListener): AutoClearDialog {
        this.listener = listener
        return this
    }


    interface IOnAutoClearDialogListener : IOnSimpleDialogListener
}