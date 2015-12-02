package com.example.stackhouse.pathtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Stackhouse on 2015/9/16.
 */
public class MyView extends View {
    //定义五种颜色
    int[] color = new int[]{Color.BLACK,Color.BLUE,Color.GREEN,Color.RED,Color.YELLOW};
    //定义五种路径特效
    PathEffect[] effects = new PathEffect[5];
    private Path path;
    private Paint paint;
    float phase;
    //定义构造方法
    public MyView(Context context) {
        super(context);
        //为paint添加属性
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
        //为path添加属性
        path = new Path();
        path.moveTo(0, 0);
        for (int i=1;i<=15;i++){
            //创建十五个随机的点，并连线
            path.lineTo(i * 20, (float) Math.random() * 60);
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //将背景填充为白色
        canvas.drawColor(Color.WHITE);
        //下面开始初始化五种路径特效
        effects[0] = null;  //无特效
        effects[1] = new CornerPathEffect(10); //使用CornerPathEffect
        effects[2] = new DiscretePathEffect(3.0f,5.0f); //使用DiscretPathEffect
        effects[3] = new DashPathEffect(new float[]{20,10,5,10},phase);  //使用DashPathEffect
        //初始化PathDashPathEffct
        Path p = new Path();
        p.addRect(0,0,8,8,Path.Direction.CCW);
        effects[4] = new PathDashPathEffect(p,12,phase,PathDashPathEffect.Style.ROTATE);

        //将画布移动到8,8处开始绘制
        canvas.translate(8,8);

        //依次使用五种路径特效和颜色来绘制
        for (int i=0;i<effects.length;i++){
            paint.setPathEffect(effects[i]);
            paint.setColor(color[i]);
            canvas.drawPath(path,paint);
            canvas.translate(0, 60);

            //改变phase的值，形成动画效果
            phase+=1;
            invalidate();
        }
    }





}
