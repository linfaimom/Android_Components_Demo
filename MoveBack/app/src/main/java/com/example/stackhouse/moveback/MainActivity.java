package com.example.stackhouse.moveback;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class MainActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }
    class MyView extends View
    {
        //记录背景位图的实际高度
        final int BACK_HEIGHT = 1700;
        //背景图片
        private Bitmap back;
        private Bitmap plane;
        //背景图片的开始位置
        final int WIDTH = 320;
        final int HEIGHT = 600;
        private int startY = BACK_HEIGHT - HEIGHT;
        private Matrix matrix = new Matrix();
        //飞机所在位置
        private float currentX = 160;
        private float currentY = 380;

        public MyView(Context context)
        {
            super(context);
            //获取窗口管理器
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            float screenWidth = metrics.widthPixels;
            float scale = screenWidth / WIDTH;
            matrix.setScale(scale,scale);
            // TODO Auto-generated constructor stub
            back = BitmapFactory.decodeResource(context.getResources()
                    , R.drawable.back);
            plane = BitmapFactory.decodeResource(context.getResources()
                    , R.drawable.plane);
            final Handler handler = new Handler()
            {
                public void handleMessage(Message msg)
                {
                    if (msg.what == 0x123)
                    {
                        //重新开始移动
                        if (startY <= 3)
                        {
                            startY = BACK_HEIGHT - HEIGHT;
                        }
                        else
                        {
                            startY -= 5;
                        }
                    }
                    invalidate();
                }
            };
            new Timer().schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    handler.sendEmptyMessage(0x123);
                }

            }, 0 , 100);
        }
        @Override
        public void onDraw(Canvas canvas)
        {
            // 根据原始位图和Matrix创建新图片
            Bitmap bitmap2 = Bitmap.createBitmap(back, 0, startY
                    , WIDTH , HEIGHT,matrix,false);
            // 绘制新位图
            canvas.drawBitmap(bitmap2, 0 , 0  , null);
            // 绘制飞机
            canvas.drawBitmap(plane, currentX , currentY  , null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            currentX = event.getX();
            currentY = event.getY();
            return true;
        }
    }
}