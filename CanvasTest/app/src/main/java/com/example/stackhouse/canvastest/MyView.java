package com.example.stackhouse.canvastest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.jar.Attributes;

/**
 * Created by Stackhouse on 2015/9/14.
 */
public class MyView extends View {
    public MyView(Context context, AttributeSet set){
        super(context,set);
    }
    @Override
    public void onDraw(Canvas canvas){
        //创建画笔
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(Color.RED);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置风格
        paint.setStyle(Paint.Style.STROKE);

        //绘制圆
        canvas.drawCircle(40,40,30,paint);
        //绘制正方形
        canvas.drawRect(10,80,70,140,paint);
        //绘制矩形
        canvas.drawRect(10,150,70,190,paint);
        //绘制圆角矩形
        RectF rectF = new RectF(10,200,70,230);
        canvas.drawRoundRect(rectF,15,15,paint);

        //使用Path绘制三角形
        Path path1 = new Path();
        path1.moveTo(10,340);
        path1.lineTo(70,340);
        path1.lineTo(40,290);
        path1.close();
        canvas.drawPath(path1,paint);
    }
}
