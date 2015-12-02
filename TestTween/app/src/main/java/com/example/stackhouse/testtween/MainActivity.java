package com.example.stackhouse.testtween;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
        Button next = (Button)findViewById(R.id.next);
        final ImageView plane = (ImageView)findViewById(R.id.image);
        //加载第一个动画资源
        final Animation anim = AnimationUtils.loadAnimation(this,R.anim.anim);
        //设置动画结束后保持结束状态
        anim.setFillAfter(true);
        //加载第二个动画资源
        final Animation reserve = AnimationUtils.loadAnimation(this,R.anim.reserve);
        //设置动画结束后保持结束状态
        reserve.setFillAfter(true);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123){
                    plane.startAnimation(reserve);
                }
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plane.startAnimation(anim);
                //设置3.5秒后启动第二个动画资源
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(0x123);
                    }
                },3500);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });



    }

}
