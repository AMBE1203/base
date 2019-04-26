package com.ambe.filerecovery.ui.view.permission;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.ambe.filerecovery.R;

import java.util.Timer;
import java.util.TimerTask;

public class PermissionActivity extends AppCompatActivity {
    private ImageView handImage;
    private Handler handler;
    SwitchCompat permissionSwitch;
    private TextView permissionTitle;
    private Animation shake;
    private Timer timer;
    private String title = "";

    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_activity);
        getWindow().setLayout(-1, -1);
        this.permissionSwitch = (SwitchCompat) findViewById(R.id.permission_switch);
        this.permissionTitle = (TextView) findViewById(R.id.permission_title);
        this.handImage = (ImageView) findViewById(R.id.handImage);
        this.shake = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
        this.permissionSwitch.setClickable(false);
        try {
            this.title = getIntent().getExtras().getString(PermissionUtils.TAG_PERMISSION_TITLE);
        } catch (Exception e) {
        }
        if (this.title != null) {
            this.permissionTitle.setText(this.title);
        }
    }

    protected void onResume() {
        super.onResume();
        setPermissionAnim();
    }

    private void setPermissionAnim() {
        this.timer = new Timer();
        this.handler = new Handler();
        this.timer.schedule(new TimerTask() {
            public void run() {
                PermissionActivity.this.handler.post(() -> {
                    if (PermissionActivity.this.permissionSwitch.isChecked()) {
                        PermissionActivity.this.permissionSwitch.setChecked(false);
                    } else if (!PermissionActivity.this.permissionSwitch.isChecked()) {
                        PermissionActivity.this.permissionSwitch.setChecked(true);
                        PermissionActivity.this.handImage.startAnimation(PermissionActivity.this.shake);
                    }
                });
            }
        }, 0, 1000);
    }

    protected void onPause() {
        super.onPause();
        try {
            this.timer.cancel();
        } catch (Exception e) {
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            this.timer.cancel();
        } catch (Exception e) {
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
