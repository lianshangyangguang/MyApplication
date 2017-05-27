package com.gwell.view.test3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView) findViewById(R.id.view);


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
        Log.d("zxy", "left: " + location[0] + "top:" + location[1] + "height:" + imageView.getHeight() + "width:"
                + imageView.getWidth());
        context.startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
