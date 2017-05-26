package com.gwell.view.test2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView) findViewById(R.id.img1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPhotoActivity(MainActivity.this, img1);
            }
        });
    }

    public void startPhotoActivity(Context context, ImageView imageView) {
        Intent intent = new Intent(context, Main2Activity.class);
        int location[] = new int[2];

        imageView.getLocationOnScreen(location);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", imageView.getHeight());
        intent.putExtra("width", imageView.getWidth());
        intent.putExtra("src", R.mipmap.ic_launcher);

        context.startActivity(intent);
        overridePendingTransition(0, 0);
    }

//    private void performExitAnimation(final DragPhotoView view, float x, float y, float w, float h) {
//        Log.d("zxy", "performExitAnimation: ");
//        view.finishAnimationCallBack();
//        float viewX = mTargetWidth / 2 + x - mTargetWidth * mScaleX / 2;
//        float viewY = mTargetHeight / 2 + y - mTargetHeight * mScaleY / 2;
//        view.setX(viewX);
//        view.setY(viewY);
//
//        float centerX = view.getX() + mOriginWidth / 2;
//        float centerY = view.getY() + mOriginHeight / 2;
//
//        float translateX = mOriginCenterX - centerX;
//        float translateY = mOriginCenterY - centerY;
//
//
//        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(view.getX(), view.getX() + translateX);
//        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                view.setX((Float) valueAnimator.getAnimatedValue());
//            }
//        });
//        translateXAnimator.setDuration(300);
//        translateXAnimator.start();
//        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(view.getY(), view.getY() + translateY);
//        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                view.setY((Float) valueAnimator.getAnimatedValue());
//            }
//        });
//        translateYAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                animator.removeAllListeners();
//                finish();
//                overridePendingTransition(0, 0);
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
//        translateYAnimator.setDuration(300);
//        translateYAnimator.start();
//    }
}
