package com.example.stackhouse.testtween;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Stackhouse on 2015/11/6.
 */
public class SecondActivity extends Activity {
    //记录蝴蝶当前位置
    private float curX = 0;
    private float curY = 30;
    //记录下一个位置
    private float nextX = 0;
    private float nextY = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        final ImageView image = (ImageView)findViewById(R.id.image);
        final AnimationDrawable anim = (AnimationDrawable)image.getBackground();
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==0x123){
                    //横向上一直向右飞
                    if(nextX>400){
                        curX=nextX=0;
                    }
                    else{
                        nextX+=8;
                    }
                    //纵向上随机
                    nextY=curY+(float)(Math.random()*10-5);
                    TranslateAnimation ta = new TranslateAnimation(curX,nextX,curY,nextY);
                    curX=nextX;
                    curY=nextY;
                    ta.setDuration(200);
                    //开始位移动画
                    image.startAnimation(ta);
                }
            }
        };
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始逐帧动画
                anim.start();
                //每过0.2秒运行一次TranslateAnimation动画使得动画位移
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(0x123);
                    }
                },0,200);

            }
        });
    }
}
