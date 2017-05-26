package com.gwell.view.myapplication;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;

import java.io.InputStream;

public class Main2Activity extends Activity {
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
        private int window_width, window_height;// 控件宽度
        private DragImageView dragImageView;// 自定义控件
        private int state_height;// 状态栏的高度

        private ViewTreeObserver viewTreeObserver;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_main2);
            final View rootView =LayoutInflater.from(this).inflate(R.layout.activity_main2,null);
            /** 获取可見区域高度 **/
            WindowManager manager = getWindowManager();
            window_width = manager.getDefaultDisplay().getWidth();
            window_height = manager.getDefaultDisplay().getHeight();

//            WindowManager.LayoutParams lp=getWindow().getAttributes();
//            lp.alpha=1;
//            getWindow().setAttributes(lp);



            dragImageView = (DragImageView) findViewById(R.id.div_main);
            ViewCompat.setTransitionName(dragImageView, "key");
            Bitmap bmp = BitmapUtil.ReadBitmapById(this, R.mipmap.ic_launcher,
                    window_width, window_height);
            // 设置图片
            dragImageView.setImageBitmap(bmp);
            dragImageView.setmActivity(this);//注入Activity.
            /** 测量状态栏高度 **/
            viewTreeObserver = dragImageView.getViewTreeObserver();
            viewTreeObserver
                    .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

                        @Override
                        public void onGlobalLayout() {
                            if (state_height == 0) {
                                // 获取状况栏高度
                                Rect frame = new Rect();
                                getWindow().getDecorView()
                                        .getWindowVisibleDisplayFrame(frame);
                                state_height = frame.top;
                                dragImageView.setScreen_H(window_height-state_height);
                                dragImageView.setScreen_W(window_width);
                            }

                        }
                    });



//            performEnterAnimation();
//            mPhotoViews[0].setMinScale(mScaleX);


            dragImageView.setOnClickListener(new DragImageView.OnClickListener() {
                @Override
                public void onClick() {
                    onBackPressed();
                }

                @Override
                public void onChangeBackground() {
                    rootView.setBackgroundColor(Color.RED);
                }
            });

        }

        /**
         * 读取本地资源的图片
         *
         * @param context
         * @param resId
         * @return
         */
        public static Bitmap ReadBitmapById(Context context, int resId) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            // 获取资源图片
            InputStream is = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(is, null, opt);
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,R.animator.close_up);
    }

    private void out(){
        ObjectAnimator translationX = ObjectAnimator.ofFloat(dragImageView, "translationX", 0, 290);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(dragImageView, "translationY", 0, 500);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(dragImageView, "scaleX", 1, 9);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(dragImageView, "scaleY", 1, 9);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(dragImageView, "alpha", 0, 1);
        scaleY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                View v = (View) dragImageView.getParent();
                v.setBackgroundColor(Color.BLACK);
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

    private void comein(){
        ObjectAnimator translationX = ObjectAnimator.ofFloat(dragImageView, "translationX", 0, 290);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(dragImageView, "translationY", 0, 500);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(dragImageView, "scaleX", 1, 10);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(dragImageView, "scaleY", 1, 12);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(dragImageView, "alpha", 0, 1);
        scaleY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                View v = (View) dragImageView.getParent();
//                v.setBackgroundColor(Color.BLACK);
//                dragImageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
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
        animatorSet.playTogether(translationX, translationY, scaleX, scaleY);
        animatorSet.setDuration(300);
        animatorSet.start();
    }
}




