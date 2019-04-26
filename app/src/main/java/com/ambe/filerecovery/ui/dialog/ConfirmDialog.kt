package com.ambe.filerecovery.ui.dialog

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import com.ambe.filerecovery.R
import com.ambe.filerecovery.interfaces.IOnSimpleDialogListener
import com.ambe.filerecovery.ui.BaseDialog
import com.ambe.filerecovery.utils.Constants
import com.ambe.filerecovery.utils.PrefUtils
import com.ambe.filerecovery.utils.Utils
import kotlinx.android.synthetic.main.dialog_auto_clear.*
import kotlinx.android.synthetic.main.dialog_confirm.*


/**
 *  Created by AMBE on 10/4/2019 at 15:43 PM.
 */
class ConfirmDialog(context: Context) : BaseDialog(context) {
    override fun setAnimation() {
        Utils.animateDialog(vgf_dialog_confirm)

    }

    private var listener: IOnConfirmDialogListener? = null
    private var pref: PrefUtils? = null


    override fun initView() {
        pref = PrefUtils.newInstance(context)
        editPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        editPass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() == "") {
                    editPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                } else {

                    if (Utils.sha256(s.toString()) == pref?.getString(Constants.PIN, "")) {
                        editPass.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_selected,
                            0
                        )

                    } else {
                        editPass.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_cancel,
                            0
                        )
                    }
                }
            }
        })


        lnlBtnCancelConfirm.setOnClickListener { listener?.onCancel() }
        lnlBtnAcceptConfirm.setOnClickListener { listener?.onAgree(editPass.text.toString()) }


    }

    fun setListener(listener: IOnConfirmDialogListener): ConfirmDialog {
        this.listener = listener
        return this
    }


    override fun getLayout(): Int {
        return R.layout.dialog_confirm
    }

    interface IOnConfirmDialogListener : IOnSimpleDialogListener


}