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
import kotlinx.android.synthetic.main.dialog_confirm.*
import kotlinx.android.synthetic.main.dialog_set_your_pass.*

/**
 *  Created by AMBE on 12/4/2019 at 13:34 PM.
 */
class SetYourPassDialog(context: Context) : BaseDialog(context) {

    private var listener: IOnSetYourPassListner? = null

    override fun setAnimation() {

        Utils.animateDialog(vgf_dialog_set_your_pass)

    }

    override fun initView() {
        editReNewPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        editNewPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        editNewPass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() == "") {
                    editNewPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                } else {
                    if (s.toString().length == 4) {
                        editNewPass.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_selected,
                            0
                        )

                    } else {
                        editNewPass.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_cancel,
                            0
                        )


                    }


                }
            }
        })

        editReNewPass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString() == "") {
                    editReNewPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                } else if (s.toString().length == 4 && s.toString() == editNewPass.text.toString()) {
                    editReNewPass.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_selected,
                        0
                    )

                    txtStatusSetYourPass.text = context.getString(R.string.correcr_password)

                } else {
                    editReNewPass.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_cancel,
                        0
                    )

                    txtStatusSetYourPass.text = context.getString(R.string.not_correct_pass)

                }
            }
        })


        lnlBtnAcceptSetYour.setOnClickListener {
            if (editNewPass.text.toString() != editReNewPass.text.toString() || editNewPass.text.length != 4 || editReNewPass.text.length != 4) {
                txtStatusSetYourPass.text = context.getString(R.string.not_correct_pass)
            } else {
                listener?.onAgree(editNewPass.text.toString())
            }
        }
        lnlBtnCancelSetYour.setOnClickListener { listener?.onCancel() }
    }

    fun setListener(listener: IOnSetYourPassListner): SetYourPassDialog {
        this.listener = listener
        return this
    }

    override fun getLayout(): Int {

        return R.layout.dialog_set_your_pass
    }

    interface IOnSetYourPassListner : IOnSimpleDialogListener
}