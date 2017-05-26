package com.gwell.view.test3;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by xiyingzhu on 2017/5/26.
 */
public class MyImageView extends ImageView {
    private Paint mPaint;

    // downX
    private float mDownX;
    // down Y
    private float mDownY;

    private float mTranslateY;
    private float mTranslateX;
    private float mScale = 1;
    private int mWidth;
    private int mHeight;
    private float mMinScale = 0f;
    private int mAlpha = 255;
    private final static int MAX_TRANSLATE_Y = 90;  //缩放时，两手指距离与此值相除，为scale

    private final static long DURATION = 300;
    //is event on PhotoView
    private boolean isTouchEvent = false;
    private String TAG = "zxy";
    private Context context;

    public MyImageView(Context context) {
        super(context, null, 0);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
        mPaint = new Paint();
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAlpha(mAlpha);
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
        canvas.translate(mTranslateX, mTranslateY);
        canvas.scale(mScale, mScale, mWidth / 2, mHeight / 2);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
    }

    private float beforeLenght, afterLenght;// 两触点距离
    private long startTime, endTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /** 处理单点、多点触摸 **/
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                long currentTime = System.currentTimeMillis();
                if (currentTime - startTime <= 1000) {
                    //双击事件
                    if (mScale < 2) {
                        mScale = 2;
                    } else {
                        mScale = 1;
                    }
                    invalidate();
                } else {
                    startTime = currentTime;
                }
                Log.d("zxy", "ACTION_DOWN: ");
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            // 多点触摸
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    Log.d(TAG, "多点触摸: ");
                    beforeLenght = getDistance(event);// 获取两点的距离
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (mTranslateY >= 0 && event.getPointerCount() == 1) {
                    onActionMove(event);
                    if (drag == 0 && zoomStatus == 0) {
                        drag = 1;
                    }
                    //如果有上下位移 则不交给viewpager
                    if (mTranslateY != 0) {
                        isTouchEvent = true;
                    }
                    return true;
                }
                if (event.getPointerCount() == 2) {
                    Log.d("zxy", "双击 ");
                    if (drag == 0 && zoomStatus == 0) {
                        zoomStatus = 1;
                    }
                    onPointerDown(event);
                }
                //in viewpager
                if (mTranslateY == 0 && mTranslateX != 0) {
                    //如果不消费事件，则不作操作
                    if (!isTouchEvent) {
                        mScale = 1;
                        return true;
                    }
                }
                //防止下拉的时候双手缩放
                if (mTranslateY >= 0 && mScale < 0.95) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d("zxy", "ACTION_UP:zoomStatus :" + zoomStatus + "event.getPointerCount()" + event.getPointerCount() + "drag:" + drag);
                endTime = System.currentTimeMillis();
                if (endTime - startTime < 100 && event.getPointerCount() == 1) {
                    Log.d(TAG, "UP1: ");
                    if (onClickListener != null) {
                        if (mScale == 1) {
                            onClickListener.onClick();
                        } else {
                            goBack();
                        }
                    }
                }
                if (zoomStatus == 1 && event.getPointerCount() == 2) {
                    zoomStatus = 0;
                    final ValueAnimator animator = ValueAnimator.ofFloat(mScale, 1);
                    animator.setDuration(300);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            mScale = (float) valueAnimator.getAnimatedValue();
                            invalidate();
                        }
                    });
                    animator.start();
                    mScale = 1;
                    drag = 0;
                }
                if (drag == 1 && event.getPointerCount() == 1 && mScale <= 1 && zoomStatus == 0) {
                    if (mTranslateY > 30 || mTranslateX > 30) {
                        Log.d(TAG, "UP2: ");
                        mAlpha = 0;
                        invalidate();
                        if (onClickListener != null) {
                            if (mScale == 1) {
                                onClickListener.onClick();
                            } else {
                                goBack();
                            }
                        }
                    } else {
                        mAlpha = 255;
                        mScale = 1;
                        mTranslateX = 0;
                        mTranslateY = 0;
                        invalidate();
                    }

                }
                if (drag == 0 || zoomStatus == 0) {
                    drag = 0;
                    zoomStatus = 0;
                }
                break;

            // 多点松开
            case MotionEvent.ACTION_POINTER_UP:
                if (zoomStatus == 1 && event.getPointerCount() == 2) {
                    zoomStatus = 0;
                    final ValueAnimator animator = ValueAnimator.ofFloat(mScale, 1);
                    animator.setDuration(300);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            mScale = (float) valueAnimator.getAnimatedValue();
                            invalidate();
                        }
                    });
                    animator.start();
                    mScale = 1;
                    invalidate();
                }
                break;

        }
        return true;

    }

    private void goBack() {
        final ValueAnimator animator = ValueAnimator.ofFloat(mTranslateY, -34);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTranslateY = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();

        final ValueAnimator animatorX = ValueAnimator.ofFloat(mTranslateX, -25);
        animatorX.setDuration(DURATION);
        animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTranslateX = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animatorX.start();

        final ValueAnimator animatorScale = ValueAnimator.ofFloat(mScale, 0.09f);
        animatorScale.setDuration(DURATION);
        animatorScale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mScale = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animatorScale.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onClickListener.onChangeBackground();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorScale.start();


    }

    public float getTranslateY() {
        return mTranslateY;
    }

    public float getTranslateX() {
        return mTranslateX;
    }

    public float getMScale() {
        return mScale;
    }

    private int drag = 0;

    private void onActionMove(MotionEvent event) {
        Log.d(TAG, " 单个:zoomStatus" + zoomStatus);
        Log.d("zxy", "onActionMove1: mTranslateY" + mTranslateY);
        if (zoomStatus == 1) {
            return;
        }
        drag = 1;
        float moveY = event.getY();
        float moveX = event.getX();
        mTranslateX = moveX - mDownX;
        mTranslateY = moveY - mDownY;
        Log.d("zxy", "onActionMove2: mTranslateY" + mTranslateY);

        //保证上划到到顶还可以继续滑动
        if (mTranslateY < 0) {
            mTranslateY = 0;
        }

        float percent = mTranslateY / MAX_TRANSLATE_Y;
        if (mScale >= mMinScale && mScale <= 1f) {
            mScale = 1 - percent;
            mAlpha = (int) (255 * (1 - percent));
            if (mAlpha > 255) {
                mAlpha = 255;
            } else if (mAlpha < 0) {
                mAlpha = 0;
            }
        }
        if (mScale < mMinScale) {
            mScale = mMinScale;
        } else if (mScale > 1f) {
            mScale = 1;
        }
        invalidate();
    }

    //测试   拖到上方的效果
    private void drag(MotionEvent event) {
        Log.d(TAG, " 单个:zoomStatus" + zoomStatus);
        Log.d("zxy", "onActionMove1: mTranslateY" + mTranslateY);
        if (zoomStatus == 1) {
            return;
        }
        drag = 1;
        float moveY = event.getY();
        float moveX = event.getX();
        mTranslateX = moveX - mDownX;
        mTranslateY = moveY - mDownY;
        Log.d("zxy", "onActionMove2: mTranslateY" + mTranslateY);

        //保证上划到到顶还可以继续滑动
        if (mTranslateY < 0) {
            mTranslateY = 0;
        }
        invalidate();
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick();

        void onChangeBackground();
    }

    /**
     * 获取两点的距离
     **/
    float getDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float) Math.sqrt(x * x + y * y);
    }

    private int zoomStatus = 0;

    /**
     * 两个手指 只能放大缩小
     **/
    void onPointerDown(MotionEvent event) {
        Log.d(TAG, "两个手指: zoomStatus" + zoomStatus);
        if (drag == 1) {
            return;
        }
        if (event.getPointerCount() == 2) {

            zoomStatus = 1;
            afterLenght = getDistance(event);// 获取两点的距离
            float gapLenght = afterLenght - beforeLenght;// 变化的长度
            Log.d(TAG, "gapLenght: " + gapLenght);
            if (Math.abs(gapLenght) > 10f) {
                mScale = afterLenght / beforeLenght;// 求的缩放的比例
                Log.d("zxy", "mScale: " + mScale);
                beforeLenght = afterLenght;
                if (mScale < mMinScale) {
                    mScale = mMinScale;
                } else if (mScale > 3f) {
                    mScale = 3;
                }
                Log.d(TAG, "mScale: " + mScale);
                invalidate();
            }
        }
    }

    private ValueAnimator getTranslateYAnimation() {
        final ValueAnimator animator = ValueAnimator.ofFloat(mTranslateY, 0);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTranslateY = (float) valueAnimator.getAnimatedValue();
            }
        });

        return animator;
    }

    private ValueAnimator getAlphaAnimation() {
        final ValueAnimator animator = ValueAnimator.ofInt(mAlpha, 255);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAlpha = (int) valueAnimator.getAnimatedValue();
            }
        });

        return animator;
    }
}
