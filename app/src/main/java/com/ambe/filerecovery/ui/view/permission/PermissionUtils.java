package com.ambe.filerecovery.ui.view.permission;

import android.content.Context;
import android.content.Intent;

public class PermissionUtils {
    public static final String TAG_PERMISSION_TITLE = "_permission_title";

    public static void showPermission(Context mContext, String title) {
        mContext.startActivity(new Intent(mContext, PermissionActivity.class).putExtra(TAG_PERMISSION_TITLE, title).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
