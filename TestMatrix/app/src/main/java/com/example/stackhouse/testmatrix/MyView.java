package com.example.stackhouse.testmatrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Stackhouse on 2015/9/28.
 */
public class MyView extends View {
    //位图的宽和高
    private int width,height;
    //设置倾斜角度
    private float sx=0.0f;
    //创建Matrix实例
    Matrix matrix = new Matrix();
    //声明Bitmap
    private Bitmap bitmap;
    //设置缩放比例
    private float scale=1.0f;
    //判断缩放与否
    private boolean isScaled=false;

    public MyView(Context context) {
        super(context);
        //获取bitmap实例
        bitmap = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.girls)).getBitmap();
        width = bitmap.getWidth();  //获取位图宽
        height = bitmap.getHeight();  //获取位图高
        this.setFocusable(true);  //使当前视图获取焦点
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //重置matrix
        matrix.reset();
        if(isScaled)
            matrix.setScale(scale,scale); //缩放matrix
        else
            matrix.setSkew(sx,0);  //旋转matrix

        //根据原始位图和matrix创建新图片
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        //重新绘制位图
        canvas.drawBitmap(bitmap1,matrix,null);
    }


}
