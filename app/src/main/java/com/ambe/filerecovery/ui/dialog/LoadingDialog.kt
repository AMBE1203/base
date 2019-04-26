package com.ambe.filerecovery.ui.dialog

import android.content.Context
import com.ambe.filerecovery.R
import com.ambe.filerecovery.ui.BaseDialog

/**
 *  Created by AMBE on 10/4/2019 at 9:00 AM.
 */
class LoadingDialog(context: Context) : BaseDialog(context) {
    override fun setAnimation() {




    }

    override fun initView() {
    }

    override fun getLayout(): Int {

        return R.layout.dialog_loading
    }
}