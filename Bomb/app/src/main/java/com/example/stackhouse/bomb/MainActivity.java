package com.example.stackhouse.bomb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.lang.reflect.Field;

import static com.example.stackhouse.bomb.R.drawable.blast;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer bomb;
    private AnimationDrawable anim;
    private MyView myView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用FrameLayout布局管理器，它允许组件自己控制位置
        final FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);
        //设置布局管理器的背景
        frameLayout.setBackgroundResource(R.drawable.back);
        //设置音效
        bomb = MediaPlayer.create(this, R.raw.bomb);
        //设置myView来显示动画
        myView = new MyView(this);
        myView.setBackgroundResource(blast);
        //设置myView默认为隐藏
        myView.setVisibility(View.INVISIBLE);
        //获取动画对象
        anim = (AnimationDrawable) myView.getBackground();
        frameLayout.addView(myView);
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //先停止动画播放
                    anim.stop();
                    float x = event.getX();
                    float y = event.getY();
                    //控制myView的显示位置
                    myView.setLocation((int)y-40,(int)x-20);
                    myView.setVisibility(View.VISIBLE);
                    //启动动画
                    anim.start();
                    //启动音效
                    bomb.start();

                }
                return false;
            }
        });
    }

    class MyView extends ImageView {
        public MyView(Context context) {
            super(context);
        }

        public void setLocation(int top, int left){
            this.setFrame(left,top,left+40,top+40);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Field field = null;
            try {
                field = AnimationDrawable.class.getDeclaredField("mCurFrame");
                field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            //获取anim动画的最后一帧
            int curFrame = 0;
            try {
                curFrame = field.getInt(anim);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //如果到了最后一帧
            if(curFrame==anim.getNumberOfFrames()-1){
                setVisibility(View.INVISIBLE);
            }
            super.onDraw(canvas);
        }
    }
}
