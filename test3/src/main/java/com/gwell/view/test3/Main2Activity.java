package com.gwell.view.test3;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    private MyImageView img2;
    int mOriginLeft;
    int mOriginTop;
    int mOriginHeight;
    int mOriginWidth;
    int mOriginCenterX;
    int mOriginCenterY;
    private float mTargetHeight;
    private float mTargetWidth;
    private float mScaleX;
    private float mScaleY;
    private float mTranslationX;
    private float mTranslationY;
    private float scale;
    String TAG ="zxy";
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_main2, null);
        img2 = (MyImageView) findViewById(R.id.view);
        img2.setImageResource(R.mipmap.ic_launcher);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        height = metric.heightPixels;   // 屏幕高度（像素）
        mOriginLeft = getIntent().getIntExtra("left", 0);
        mOriginTop = getIntent().getIntExtra("top", 0);
        mOriginHeight = getIntent().getIntExtra("height", 0);
        mOriginWidth = getIntent().getIntExtra("width", 0);
        mOriginCenterX = mOriginLeft + mOriginWidth / 2;
        mOriginCenterY = mOriginTop + mOriginHeight / 2;

        Log.d(TAG, "traY: "+(height/2-mOriginCenterY));
        Log.d("zxy", "jisuan:width:"+(width/2-mOriginCenterX)+"height:"+((height)/2-mOriginCenterY));

        int[] location = new int[2];

        img2.getLocationOnScreen(location);

        mTargetHeight = height;
        mTargetWidth = width;


        mScaleX = (float)  mTargetWidth/ mOriginWidth;
        mScaleY = (float)  mTargetHeight/  mOriginHeight;

        scale = mScaleX>mScaleY?mScaleX:mScaleY;

        float targetCenterX = mTargetWidth / 2;
        float targetCenterY = mTargetHeight / 2;

        mTranslationX =  targetCenterX-  mOriginCenterX;
        mTranslationY = mOriginCenterY - targetCenterY;


        Log.d(TAG, "mTranslationX: "+mTranslationX+"mTranslationY:"+(height-mOriginTop)/2);

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
        out();
        overridePendingTransition(0, R.anim.dismiss);
    }

    private void out() {
//        img2.goBack();
//        finish();
//        overridePendingTransition(0, R.anim.dismiss);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(img2, "translationX", img2.getTranslationX(), 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(img2, "translationY", img2.getTranslationY(), 100);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(img2, "scaleX", img2.getScaleX(), 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(img2, "scaleY", img2.getScaleY(), 1);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(img2, "alpha", 255,0);
        scaleY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                img2.setVisibility(View.GONE);
                img2.setImageResource(R.mipmap.ic_launcher);
                img2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationX, translationY, scaleX, scaleY,alpha);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    private void comein() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(img2, "translationX",0,300);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(img2, "translationY",0,520);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(img2, "scaleX",1,12);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(img2, "scaleY",1,12);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(img2, "alpha", 0, 255);
        scaleY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                View v = (View) img2.getParent();
//                v.setBackgroundColor(Color.BLACK);
//                img2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether( scaleX, scaleY,alpha,translationX,translationY);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

}
