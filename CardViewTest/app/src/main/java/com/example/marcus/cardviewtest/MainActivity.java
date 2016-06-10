package com.example.marcus.cardviewtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class MainActivity extends Activity {
    private int WIDTH;
    private int HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPixel();
        setContentView(new Test(this));
    }

    public void getPixel() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        WIDTH = metrics.widthPixels;
        HEIGHT = metrics.heightPixels;
    }

    private class Test extends View {
        private Paint[] paints =  new Paint[4];

        public Test(Context context) {
            super(context);
            for (int i=0; i<paints.length; i++) {
                paints[i] = new Paint();
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //draw circle
            paints[0].setAntiAlias(true);
            paints[0].setStyle(Paint.Style.STROKE);
            paints[0].setStrokeWidth(5);
            canvas.drawCircle(WIDTH/2,HEIGHT/2,WIDTH/2,paints[0]);

            //draw 刻度盘
            for (int i=0;i<24;i++) {
                if (i==0||i==6||i==12||i==18) {
                    paints[1].setStrokeWidth(3);
                    paints[1].setTextSize(30);
                    canvas.drawLine(WIDTH/2,HEIGHT/2-WIDTH/2,WIDTH/2,HEIGHT/2-WIDTH/2+60,paints[1]);
                    String text = String.valueOf(i);
                    canvas.drawText(text,WIDTH/2-paints[1].measureText(text)/2,
                            HEIGHT/2-WIDTH/2+90,paints[1]);
                }
                else {
                    paints[1].setStrokeWidth(3);
                    paints[1].setTextSize(15);
                    canvas.drawLine(WIDTH/2,HEIGHT/2-WIDTH/2,WIDTH/2,HEIGHT/2-WIDTH/2+30,paints[1]);
                    String text = String.valueOf(i);
                    canvas.drawText(text,WIDTH/2-paints[1].measureText(text)/2,
                            HEIGHT/2-WIDTH/2+60,paints[1]);
                }
                //以屏幕中心为原点旋转画布,每次旋转15度,这样就能复用以上代码把表盘刻满
                canvas.rotate(15,WIDTH/2,HEIGHT/2);
            }
            
            //draw 指针
            paints[2].setStrokeWidth(15);   //hour
            paints[3].setStrokeWidth(10);   //minute
            //保存现在的画布状态
            canvas.save();
            //将画布平移到圆心位置,这时候起点变为了圆心
            canvas.translate(WIDTH/2,HEIGHT/2);
            canvas.drawLine(0,0,100,100,paints[2]);
            canvas.drawLine(0,0,100,150,paints[3]);
            //将此刻状态应用到直接保存的状态中
            canvas.restore();
        }
    }
}
