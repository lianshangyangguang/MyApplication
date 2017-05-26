package com.gwell.view.test3;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView) findViewById(R.id.view);
        ViewCompat.setTransitionName(img1, "key");
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//                startActivity(intent);
                startPhotoActivity(MainActivity.this,img1);
            }
        });

    }

    public  void startPhotoActivity(Context context, ImageView imageView) {
        Intent intent = new Intent(context, Main2Activity.class);
        int location[] = new int[2];

        imageView.getLocationOnScreen(location);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", imageView.getHeight());
        intent.putExtra("width", imageView.getWidth());
        intent.putExtra("src", R.mipmap.ic_launcher);

        context.startActivity(intent);
        overridePendingTransition(0,0);

    }

    int DURATION = 700;

    private ValueAnimator getAlphaAnimation() {
        final ValueAnimator animator = ValueAnimator.ofInt(0, 255);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
              int   mAlpha = (int) valueAnimator.getAnimatedValue();
                img1.setAlpha(mAlpha);
            }
        });

        return animator;
    }

    private ValueAnimator getTranslateYAnimation() {
        final ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
              float   mTranslateY = (float) valueAnimator.getAnimatedValue();
                img1.setTranslationY(mTranslateY);
            }
        });

        return animator;
    }

    private ValueAnimator getTranslateXAnimation() {
        final ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float mTranslateX = (float) valueAnimator.getAnimatedValue();
                img1.setTranslationX(mTranslateX);
            }
        });

        return animator;
    }

//    private ValueAnimator getScaleAnimation() {
//        final ValueAnimator animator = ValueAnimator.ofFloat(1, 7);
//        animator.setDuration(DURATION);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float mScale = (float) valueAnimator.getAnimatedValue();
//                invalidate();
//            }
//        });
//
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//                isAnimate = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                isAnimate = false;
//                animator.removeAllListeners();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//        return animator;
//    }

}
