package com.example.administrator.animatortest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends Activity {
    static final float BALL_SIZE = 50F;
    //从顶端下落到底端的总时间
    static final float FULL_TIME = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LinearLayout container = (LinearLayout)findViewById(R.id.container);
        //设置该窗口显示MyAnimationView组件
        container.addView(new MyAnimationView(this));

    }
    public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener{
        public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
        public MyAnimationView(Context context) {
            super(context);
            setBackgroundColor(Color.WHITE);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //如果触摸事件不是按下并移动事件
            if(event.getAction()!=MotionEvent.ACTION_DOWN && event.getAction()!=MotionEvent.ACTION_MOVE){
                return false;
            }
            //在事件发生点添加一个小球（用一个圆形代表）
            ShapeHolder newBall = addBall(event.getX(),event.getY());
            //获取屏幕高度
            float h = (float)getHeight();
            //计算刚下落时的Y
            float startY = newBall.getY();
            //结束时的Y
            float endY = getHeight()-BALL_SIZE;

            //点击位置的Y
            float eventY = event.getY();
            //计算从当前点击位置到下落结束点动画的持续时间
            int duration = (int)(((h-eventY) / h) * FULL_TIME);

            //定义小球落下时的动画，让newBall对象的y属性从事件发生地点变化到屏幕下方
            ValueAnimator fallAnim = ObjectAnimator.ofFloat(newBall,"y",startY,endY);
            //设置动画时间
            fallAnim.setDuration(duration);
            //设置插值方式为加速插值
            fallAnim.setInterpolator(new AccelerateInterpolator());
            //为其添加监听器,当ValueAnimator的属性发生变化时触发监听器
            fallAnim.addUpdateListener(this);

            //定义小球渐隐(alpha值从1至0)
            ObjectAnimator fadeAnim = new ObjectAnimator().ofFloat(newBall,"alpha",1f,0f);
            fadeAnim.setDuration(250);
            //为fadeAnim设置监听器
            fadeAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //动画结束时将动画关联的ShapeHolder删除
                    balls.remove(((ObjectAnimator)animation).getTarget());
                }
            });
            fadeAnim.addUpdateListener(this);
            //定义一个AnimatorSet来讲fallAnim和fadeAnim组合在一起
            AnimatorSet set = new AnimatorSet();
            set.play(fallAnim).before(fadeAnim);
            set.start();
            return true;
        }

        public ShapeHolder addBall(float x, float y){
            //创建一个椭圆
            OvalShape circle = new OvalShape();
            //设置该椭圆的宽和高
            circle.resize(BALL_SIZE,BALL_SIZE);
            //将该椭圆包装为一个Drawable对象
            ShapeDrawable drawable = new ShapeDrawable(circle);
            //创建一个ShapeHolder对象
            ShapeHolder holder = new ShapeHolder(drawable);
            //设置ShapeHolder的x,y坐标
            holder.setX(x - BALL_SIZE / 2);
            holder.setY(y - BALL_SIZE / 2);
            int red = (int)(Math.random() * 255);
            int green = (int)(Math.random() * 255);
            int blue = (int)(Math.random() * 255);
            //将三个随机数组成ARGB颜色
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            //获取drawable上关联的画笔
            Paint paint = drawable.getPaint();
            //将三个随机数各除以4所得的商组成ARGB颜色
            int drakColor = 0xff000000 | red/4 << 16 | green/4 << 8 | blue/4;
            //创建圆形渐变
            RadialGradient gradient = new RadialGradient(37.5f,12.5f,BALL_SIZE,color,drakColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            //为ShapeHolder设置画笔
            holder.setPaint(paint);
            //集合里的balls都用这个渐变效果
            balls.add(holder);
            return holder;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //遍历balls集合里的ShapeHolder对象
            for (ShapeHolder holder : balls){
                //保存canvas当前的坐标
                canvas.save();
                //将画布转到ShapeHolder的x,y坐标
                canvas.translate(holder.getX(),holder.getY());
                //将ShapeHolder里的圆画到Canvas对象里
                holder.getShape().draw(canvas);
                //恢复Canva坐标系统
                canvas.restore();
            }
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //重绘
            this.invalidate();
        }
    }
}
