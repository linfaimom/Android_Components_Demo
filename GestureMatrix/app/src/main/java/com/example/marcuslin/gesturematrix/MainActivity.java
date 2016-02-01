package com.example.marcuslin.gesturematrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    ImageView image;
    GestureDetector detector;
    Matrix matrix;
    Bitmap imageSource;
    int width,height;
    float currentScale = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matrix = new Matrix();
        detector = new GestureDetector(this,this);
        imageSource = BitmapFactory.decodeResource(this.getResources(),R.drawable.lijiang);
        width = imageSource.getWidth();
        height = imageSource.getHeight();
        image = (ImageView)findViewById(R.id.image);
        image.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.lijiang));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        velocityX = velocityX > 4000 ? 4000 : velocityX;
        velocityX = velocityX < -4000 ? -4000 : velocityX;
        //根据手势的速度来缩放，如果velocityX>0,则放大，否则缩小
        currentScale += currentScale * velocityX / 4000.f;
        currentScale = currentScale > 0.01 ? currentScale : 0.01f;
        matrix.reset();
        matrix.setScale(currentScale,currentScale,160,200);
        //如果图片未回收，则强制回收
        BitmapDrawable afterMatrix = (BitmapDrawable) image.getDrawable();
        if (!afterMatrix.getBitmap().isRecycled()){
            afterMatrix.getBitmap().recycle();
        }
        Bitmap result = Bitmap.createBitmap(imageSource,0,0,width,height,matrix,true);
        image.setImageBitmap(result);
        return true;
    }
}
