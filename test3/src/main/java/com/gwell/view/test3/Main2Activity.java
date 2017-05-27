package com.gwell.view.test3;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

public class Main2Activity extends AppCompatActivity {

    private MyImageView img2;
    private int mOriginLeft;
    private int mOriginTop;
    private int mOriginHeight;
    private int mOriginWidth;
    private int mOriginCenterX;
    private int mOriginCenterY;
    private float mScaleX;
    private float mScaleY;
    private float mTranslationX;
    private float mTranslationY;
    private float scale;
    private String TAG = "zxy";
    private int height; // 屏幕高度（像素）
    private int width;  // 屏幕宽度（像素）
    int statusBarHeight1 = -1;

    //暂时第二个activity中imageView的初始位置需要与上一个activity中相同
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        img2 = (MyImageView) findViewById(R.id.view);

        //由于此处为NoActionBar   应用区域（）
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metric);
        width = metric.widthPixels;
        height = metric.heightPixels;

        //状态栏高度

        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("zxy", "状态栏-方法1:" + statusBarHeight1);


        mOriginLeft = getIntent().getIntExtra("left", 0);
        mOriginTop = getIntent().getIntExtra("top", 0);
        mOriginHeight = getIntent().getIntExtra("height", 0);
        mOriginWidth = getIntent().getIntExtra("width", 0);
        int src = getIntent().getIntExtra("src", -1);


        if (src != -1) {
            //获取上一个界面的图片信息，传入
            img2.setImageResource(src);
        } else {
            //设置占位图片
            img2.setImageResource(R.mipmap.ic_launcher);
        }


        mOriginCenterX = mOriginLeft + mOriginWidth / 2;
        mOriginCenterY = mOriginTop + mOriginHeight / 2;

        mScaleX = width / mOriginWidth;
        mScaleY = height / mOriginHeight;

        scale = mScaleX > mScaleY ? mScaleX : mScaleY;

        float targetCenterX = width / 2;
        float targetCenterY = height / 2;

        mTranslationX = targetCenterX - mOriginCenterX;
        mTranslationY = targetCenterY - mOriginCenterY;
        mTranslationY += statusBarHeight1/2;
        img2.setGoBack(-mTranslationX, -mTranslationY);
        Log.d(TAG, "mTranslationX: " + mTranslationX + "mTranslationY:" + mTranslationY);

        comein();
        img2.setOnClickListener(new MyImageView.OnClickListener() {
            @Override
            public void onExit() {
                finish();
                overridePendingTransition(0, R.anim.dismiss);
            }
        });

    }

    @Override
    public void onBackPressed() {
        img2.goBack();
        overridePendingTransition(0, R.anim.dismiss);
    }


    private void comein() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(img2, "translationX", 0, mTranslationX);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(img2, "translationY", 0, mTranslationY);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(img2, "scaleX", 1, scale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(img2, "scaleY", 1, scale);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(img2, "alpha", 0, 255);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, alpha, translationY, translationX);
        animatorSet.setDuration(500);
        animatorSet.start();
        img2.alpha0to1();
    }

}
