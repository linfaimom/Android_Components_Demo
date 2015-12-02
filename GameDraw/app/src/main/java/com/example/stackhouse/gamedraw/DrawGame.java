package com.example.stackhouse.gamedraw;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
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
public class DrawGame extends Activity
{
    // 桌面的宽度
    private int tableWidth;
    // 桌面的高度
    private int tableHeight;
    // 球拍的垂直位置
    private int racketY;
    // 下面定义球拍的高度和宽度
    private final int RACKET_HEIGHT = 20;
    private final int RACKET_WIDTH = 70;
    // 小球的大小
    private final int BALL_SIZE = 12;
    // 小球纵向的运行速度
    private int ySpeed = 20;
    Random rand = new Random();
    // 返回一个-0.5~0.5的比率，用于控制小球的运行方向。
    private double xyRate = rand.nextDouble() - 0.5;
    // 小球横向的运行速度
    private int xSpeed = (int) (ySpeed * xyRate * 2);
    // ballX和ballY代表小球的座标
    private int ballX = rand.nextInt(200) + 20;
    private int ballY = rand.nextInt(10) + 20;
    // racketX代表球拍的水平位置
    private int racketX = rand.nextInt(200);
    // 游戏是否结束的旗标
    private boolean isLose = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 创建PlaneView组件
        final GameView gameView = new GameView(this);
        setContentView(gameView);
        // 获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        // 获得屏幕宽和高
        tableWidth = display.getWidth();  //private int main
        tableHeight = display.getHeight();
        racketY = tableHeight - 80;
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                if (msg.what == 0x123)
                {
                    gameView.invalidate();
                }
            }
        };

        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                racketX = (int)event.getX();
                gameView.invalidate();
                return true;
            }
        });

        final Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {

            @Override
            public void run()
            {
                // 如果小球碰到左边边框
                if (ballX <= 0 || ballX >= tableWidth - BALL_SIZE)
                {
                    xSpeed = -xSpeed;
                }
                // 如果小球高度超出了球拍位置，且横向不在球拍范围之内，游戏结束。
                if (ballY >= racketY - BALL_SIZE
                        && (ballX < racketX || ballX > racketX + RACKET_WIDTH))
                {
                    timer.cancel();
                    // 设置游戏是否结束的旗标为true。
                    isLose = true;
                }
                // 如果小球位于球拍之内，且到达球拍位置，小球反弹
                else if (ballY <= 0
                        || (ballY >= racketY - BALL_SIZE && ballX > racketX && ballX <= racketX
                        + RACKET_WIDTH))
                {
                    ySpeed = -ySpeed;
                }
                // 小球座标增加
                ballY += ySpeed;
                ballX += xSpeed;
                // 发送消息，通知系统重绘组件
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 100);
    }

    class GameView extends View
    {
        public GameView(Context context)
        {
            super(context);
            setFocusable(true);
        }

        // 重写View的onDraw方法，实现绘画
        public void onDraw(Canvas canvas)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            // 如果游戏已经结束
            if (isLose)
            {
                paint.setColor(Color.RED);
                paint.setTextSize(40);
                canvas.drawText("游戏已结束",tableWidth/2-100, 200, paint);
            }
            // 如果游戏还未结束
            else
            {
                // 设置颜色，并绘制小球
                paint.setColor(Color.rgb(240, 240, 80));
                canvas.drawCircle(ballX, ballY, BALL_SIZE, paint);
                // 设置颜色，并绘制球拍
                paint.setColor(Color.rgb(80, 80, 200));
                canvas.drawRect(racketX, racketY, racketX + RACKET_WIDTH,
                        racketY + RACKET_HEIGHT, paint);
            }
        }
    }
}