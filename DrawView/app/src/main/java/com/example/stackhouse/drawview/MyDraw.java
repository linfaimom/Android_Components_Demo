package com.example.stackhouse.drawview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Stackhouse on 2015/9/22.
 */
public class MyDraw extends View {
        private float preX,preY;  //定义存放前一个位置的变量
        Paint paint;  //声明一个Paint对象
        private Path path;
        final int WIDTH=320,HEIGHT=480;  //定义决定View大小的两个常量
        Bitmap cacheBitmap = null;  //定义一个内存中的图片以作为缓存区
        Canvas cacheCanvas = null;  //定义cacheBitmap上的Canvas对象

        //定义构造方法
        public MyDraw(Context context,AttributeSet attributeSet){
            super(context,attributeSet);
            //将paint实例化并初始化
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);

            //创建一个与该View相同大小的缓存区
            cacheBitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
            //设置cacheCanvas将会绘制到内存中的cacheBitmap
            cacheCanvas = new Canvas();
            cacheCanvas.setBitmap(cacheBitmap);

            path = new Path();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x,y;
            x = event.getX();
            y = event.getY();
            //对动作进行判断并作出反应
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN :
                    path.moveTo(x,y);
                    preX = x;
                    preY = y;
                    break;

                case MotionEvent.ACTION_MOVE :
                    path.quadTo(preX,preY,x,y);
                    preX = x;
                    preY = y;
                    break;

                case MotionEvent.ACTION_UP :
                    cacheCanvas.drawPath(path, paint);  //在缓存中绘制路径
                    path.reset();
                    break;
            }
            invalidate();
            return true;  //返回true值，说明这个回调方法已经处理该事件
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Paint bitmapPaint = new Paint();  //不加特技
            //将cacheBitmap从缓存中绘制到该View组件
            canvas.drawBitmap(cacheBitmap,0,0,bitmapPaint);
            //沿着路径绘制
            canvas.drawPath(path,paint);
        }
}
