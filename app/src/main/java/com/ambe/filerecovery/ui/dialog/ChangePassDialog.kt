package com.ambe.filerecovery.ui.dialog

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.ambe.filerecovery.R
import com.ambe.filerecovery.interfaces.IOnSimpleDialogListener
import com.ambe.filerecovery.ui.BaseDialog
import com.ambe.filerecovery.utils.Constants
import com.ambe.filerecovery.utils.PrefUtils
import com.ambe.filerecovery.utils.Utils
import kotlinx.android.synthetic.main.dialog_change_pass.*
import kotlinx.android.synthetic.main.dialog_set_your_pass.*

/**
 *  Created by AMBE on 12/4/2019 at 14:48 PM.
 */
class ChangePassDialog(context: Context) : BaseDialog(context) {

    private var pref: PrefUtils? = null
    override fun setAnimation() {
        Utils.animateDialog(vgf_dialog_change_your_pass)
    }

    override fun initView() {

        pref = PrefUtils.newInstance(context)
        editNewPassChangeYour.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        editReNewPassChangeYour.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        editNewPassChangeYour.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() == "") {
                    editNewPassChangeYour.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                } else {
                    if (s.toString().length == 4) {
                        editNewPassChangeYour.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_selected,
                            0
                        )

                    } else {
                        editNewPassChangeYour.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_cancel,
                            0
                        )


                    }


                }
            }
        })

        editReNewPassChangeYour.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString() == "") {
                    editReNewPassChangeYour.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                } else if (s.toString().length == 4 && s.toString() == editNewPassChangeYour.text.toString()) {
                    editReNewPassChangeYour.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_selected,
                        0
                    )


                } else {
                    editReNewPassChangeYour.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_cancel,
                        0
                    )


                }
            }
        })

        lnlBtnAcceptChangeYour.setOnClickListener {

            if (Utils.sha256(editNewPassChangeYour.text.toString()) != pref?.getString(
                    Constants.PIN,
                    ""
                ) && editNewPassChangeYour.text.toString() == editReNewPassChangeYour.text.toString() && editNewPassChangeYour.text.length == 4
            ) {
                listener?.onAgree(editReNewPassChangeYour.text.toString())
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.new_pass_must),
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        lnlBtnCancelChangeYour.setOnClickListener {
            listener?.onCancel()
        }
    }

    override fun getLayout(): Int {
        return R.layout.dialog_change_pass
    }

    private var listener: IOnChangePassDialog? = null

    fun setListener(listener: IOnChangePassDialog): ChangePassDialog {
        this.listener = listener
        return this
    }

    interface IOnChangePassDialog : IOnSimpleDialogListener
}