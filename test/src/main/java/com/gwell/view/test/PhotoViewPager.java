/*
 * FixMultiViewPager 2016-12-26
 * Copyright (c) 2016 suzeyu Co.Ltd. All right reserved
 */
package com.gwell.view.test;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class PhotoViewPager extends ViewPager {
    private static final String TAG = PhotoViewPager.class.getSimpleName();

    public PhotoViewPager(Context context) {
        super(context);
    }

    public PhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            Log.w(TAG, "onInterceptTouchEvent() ", ex);
//            ex.printStackTrace();
        }
        return false;
    }




}
