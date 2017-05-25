package com.gwell.view.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView img1;
    private boolean b;
    public static final String IMAGE_URL_EXTRA = "IMAGE_URL_EXTRA";
    public static final String VIEW_INFO_EXTRA = "VIEW_INFO_EXTRA";
    public static final String PROPNAME_SCREENLOCATION_LEFT = "PROPNAME_SCREENLOCATION_LEFT";
    public static final String PROPNAME_SCREENLOCATION_TOP = "PROPNAME_SCREENLOCATION_TOP";
    public static final String PROPNAME_WIDTH = "PROPNAME_WIDTH";
    public static final String PROPNAME_HEIGHT = "PROPNAME_HEIGHT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView) findViewById(R.id.img1);


//        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
//        photoView.setImageResource(R.drawable.image);
//        img1.setOnClickListener(new DragImageView.OnClickListener() {
//            @Override
//            public void onClick() {
////                if (!b) {
////                    big();
////                    b = true;
////                } else {
////                    small();
////                    b = false;
////                }
//                big();
//            }

//            @Override
//            public void onChangeBackground() {
//
//            }
//        });
//    }
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);  //先得到构造器
//                builder.setView(R.layout.activity_main2);
//                builder.create().show();

                //获取数据(AdapterView的getItemAtPosition调用的就是Adapter的getItem()....我才知道...)

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                //传入选中的Item对应的ID
                intent.putExtra("id", 1);

                //主要的语句
                //通过makeSceneTransitionAnimation传入多个Pair
                //每个Pair将一个当前Activity的View和目标Activity中的一个Key绑定起来
                //在目标Activity中会调用这个Key
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this,
                        new Pair<View, String>(v,
                                "key")
                    );
                // ActivityCompat是android支持库中用来适应不同android版本的
                ActivityCompat.startActivity(MainActivity.this, intent, activityOptions.toBundle());
                overridePendingTransition(R.animator.start_down,0);


//               if (!b){
//                   big();
//                   b = true;
//               }else {
//                   small();
//                   b = false;
//               }

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

    private void big() {
//        //通过加载XML动画设置文件来创建一个Animation对象；
//        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.big);
//        //得到一个LayoutAnimationController对象；
//        LayoutAnimationController controller = new LayoutAnimationController(animation);
//        //设置控件显示的顺序；
//        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        //设置控件显示间隔时间；
//        controller.setDelay(0.3f);
//        //为ListView设置LayoutAnimationController属性；
//        animation.setFillAfter(true);
//        img1.setAnimation(animation);
//        img1.startAnimation(animation);
//
//        ObjectAnimator//
//                .ofFloat(img1, "scaleX", 1, 8)
//                .setDuration(700)//
//                .start();
//        ObjectAnimator//
//                .ofFloat(img1, "scaleY", 1, 8)
//                .setDuration(700)//
//                .start();
//        ObjectAnimator//
//                .ofFloat(img1, "alpha", 0, 1)
//                .setDuration(700)//
//                .start();
//        ObjectAnimator//
//                .ofFloat(img1, "translateX", 0, 200)
//                .setDuration(700)//
//                .start();
//        ObjectAnimator//
//                .ofFloat(img1, "translateY", 0, 200)
//                .setDuration(700)//
//                .start();

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(img1, "translationX", 0, 200);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(img1, "translationY", 0, 200);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(img1, "scaleX", 1, 8);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(img1, "scaleY", 1, 8);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY,scaleX,scaleY);

        // ObjectAnimator animatorSet = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0, i * 100F);
        animatorSet.setDuration(2000);
//        animatorSet.setInterpolator(new OvershootInterpolator());
//        animatorSet.setStartDelay(1000);
        animatorSet.start();
    }

    private void small() {
        //通过加载XML动画设置文件来创建一个Animation对象；
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.small);
        animation.setFillAfter(true);
        img1.setAnimation(animation);
        img1.startAnimation(animation);
    }

    private Bundle captureValues(@NonNull View view) {
        Bundle b = new Bundle();
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        b.putInt(PROPNAME_SCREENLOCATION_LEFT, screenLocation[0]);
        b.putInt(PROPNAME_SCREENLOCATION_TOP, screenLocation[1] - 200);
        b.putInt(PROPNAME_WIDTH, view.getWidth());
        b.putInt(PROPNAME_HEIGHT, view.getHeight());
//        //测试
//        b.putString(IMAGE_URL_EXTRA, photoPath);

        return b;
    }
}
