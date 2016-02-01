package com.example.marcuslin.gestureflipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    ViewFlipper flipper;
    GestureDetector detector;
    Animation[] animation;
    final int FLIP_DISTANCE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detector = new GestureDetector(this,this);
        flipper = (ViewFlipper)findViewById(R.id.flipper);
        flipper.addView(createImageView(R.drawable.java));
        flipper.addView(createImageView(R.drawable.classic));
        flipper.addView(createImageView(R.drawable.ee));
        flipper.addView(createImageView(R.drawable.xml));
        animation = new Animation[4];
        animation[0] = AnimationUtils.loadAnimation(this,R.anim.left_in);
        animation[1] = AnimationUtils.loadAnimation(this,R.anim.left_out);
        animation[2] = AnimationUtils.loadAnimation(this,R.anim.right_in);
        animation[3] = AnimationUtils.loadAnimation(this,R.anim.right_out);
    }

    private View createImageView(int id){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView;
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
        //如果从右向左滑动
        if (e1.getX()-e2.getX()>FLIP_DISTANCE){
            flipper.setInAnimation(animation[0]);
            flipper.setOutAnimation(animation[1]);
            flipper.showPrevious();
        }
        else if (e2.getX()-e1.getX()>FLIP_DISTANCE){
            flipper.setInAnimation(animation[2]);
            flipper.setInAnimation(animation[3]);
            flipper.showNext();
        }
        return false;
    }
}
