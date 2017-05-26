package com.gwell.view.test3;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_main2, null);
        img2 = (MyImageView) findViewById(R.id.view);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
//        ViewCompat.setTransitionName(img2, "key");
        mOriginLeft = getIntent().getIntExtra("left", 0);
        mOriginTop = getIntent().getIntExtra("top", 0);
        mOriginHeight = getIntent().getIntExtra("height", 0);
        mOriginWidth = getIntent().getIntExtra("width", 0);
        mOriginCenterX = mOriginLeft + mOriginWidth / 2;
        mOriginCenterY = mOriginTop + mOriginHeight / 2;

        int[] location = new int[2];

        img2.getLocationOnScreen(location);

        mTargetHeight = height;
        mTargetWidth = width;
        mScaleX = (float)  mTargetWidth/ mOriginWidth;
        mScaleY = (float)  mTargetHeight/  mOriginHeight;

        float targetCenterX = location[0] + mTargetWidth / 2;
        float targetCenterY = location[1] + mTargetHeight / 2;

        mTranslationX = mOriginCenterX - targetCenterX;
        mTranslationY = mOriginCenterY - targetCenterY;
//        img2.setTranslationX(mTranslationX);
//        img2.setTranslationY(mTranslationY);
//
//        img2.setScaleX(mScaleX);
//        img2.setScaleY(mScaleY);

//        performEnterAnimation();
        comein();
        img2.setOnClickListener(new MyImageView.OnClickListener() {
            @Override
            public void onClick() {
               out();
            }

            @Override
            public void onChangeBackground() {
                out2();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishWithAnimation();
    }

    private void out() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(img2, "translationX", img2.getTranslationX(), 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(img2, "translationY", img2.getTranslationY(), 100);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(img2, "scaleX", img2.getScaleX(), 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(img2, "scaleY", img2.getScaleY(), 1);
        scaleY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                img2.setAlpha(255);
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
        animatorSet.playTogether(translationX, translationY, scaleX, scaleY);
        animatorSet.setDuration(300);
        animatorSet.start();
    }

    private void out2() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(img2, "translationX", img2.getTranslationX(), 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(img2, "translationY", img2.getTranslationY(), 70);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(img2, "scaleX", img2.getScaleX(), 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(img2, "scaleY", img2.getScaleY(), 0.5f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(img2, "alpha", 1, 0);
        scaleY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

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
        animatorSet.playTogether(translationX, translationY, scaleX, scaleY);
        animatorSet.setDuration(300);
        animatorSet.start();
    }

    private void comein() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(img2, "translationX",0,300);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(img2, "translationY",0,520);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(img2, "scaleX",1,12);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(img2, "scaleY",1,12);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(img2, "alpha", 0, 1);
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
        animatorSet.playTogether( scaleX, scaleY,translationX,translationY);
        animatorSet.setDuration(300);
        animatorSet.start();
    }

    private void finishWithAnimation() {


        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(0, mTranslationX);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                img2.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(300);
        translateXAnimator.start();

        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(0, mTranslationY);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                img2.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.setDuration(300);
        translateYAnimator.start();

        ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(1, mScaleY);
        scaleYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                img2.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleYAnimator.setDuration(300);
        scaleYAnimator.start();

        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(1, mScaleX);
        scaleXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                img2.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });

        scaleXAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.removeAllListeners();
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        scaleXAnimator.setDuration(300);
        scaleXAnimator.start();
    }

    private void performEnterAnimation() {
        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(img2.getX(), 0);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                img2.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(300);
        translateXAnimator.start();

        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(img2.getY(), 0);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                img2.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.setDuration(300);
        translateYAnimator.start();

        ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(mScaleY, 1);
        scaleYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                img2.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleYAnimator.setDuration(300);
        scaleYAnimator.start();

        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(mScaleX, 1);
        scaleXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                img2.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleXAnimator.setDuration(300);
        scaleXAnimator.start();
    }
    private void performExitAnimation(final MyImageView view, float x, float y, float w, float h) {
//        view.finishAnimationCallBack();
        float viewX = mTargetWidth / 2 + x - mTargetWidth * mScaleX / 2;
        float viewY = mTargetHeight / 2 + y - mTargetHeight * mScaleY / 2;
        view.setX(viewX);
        view.setY(viewY);

        float centerX = view.getX() + mOriginWidth / 2;
        float centerY = view.getY() + mOriginHeight / 2;

        float translateX = mOriginCenterX - centerX;
        float translateY = mOriginCenterY - centerY;


        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(view.getX(), view.getX() + translateX);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(300);
        translateXAnimator.start();
        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(view.getY(), view.getY() + translateY);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.removeAllListeners();
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        translateYAnimator.setDuration(300);
        translateYAnimator.start();
    }

}
