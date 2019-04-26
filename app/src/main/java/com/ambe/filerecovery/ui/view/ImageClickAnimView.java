package com.ambe.filerecovery.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.ambe.filerecovery.R;

public class ImageClickAnimView extends AppCompatImageView {

    public ImageClickAnimView(Context context) {
        super(context);
        init(null);
    }

    public ImageClickAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ImageClickAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.GPSImageView, 0, 0);
        boolean clickAble = array.getBoolean(R.styleable.GPSImageView_clickAble, true);
        array.recycle();
        setClickable(clickAble);
        if (clickAble) {
            setBackgroundResource(R.drawable.ripple_click_img);
        }
    }
}
