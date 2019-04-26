package com.ambe.filerecovery.ui.dialog

import android.content.Context
import com.ambe.filerecovery.R
import com.ambe.filerecovery.ui.BaseDialog

/**
 *  Created by AMBE on 12/4/2019 at 15:34 PM.
 */
class NotiDialog(context: Context) : BaseDialog(context) {
    override fun setAnimation() {
    }

    override fun initView() {
    }

    override fun getLayout(): Int {

        return R.layout.dialog_noti
    }
}