package com.example.stackhouse.myanimation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by Stackhouse on 2015/11/8.
 */
public class MyAnimation extends Animation{
    private float centerX;
    private float centerY;
    //设置持续时间
    private int duration;
    public Camera camera = new Camera();

    public MyAnimation(float x, float y, int duration) {
        centerX = x;
        centerY = y;
        this.duration = duration;
    }



    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(duration);
        setFillAfter(true);
        setInterpolator(new LinearInterpolator());
    }

    /*该方法的interpolator代表了抽象的动画持续时间，
     * 不管动画实际持续时间多长，interpolatedTime参数总是从0（动画开始）变化到1（动画结束）,
     *Transformation参数代表了对目标组件所做的改变
     */

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        camera.save();
        //根据interpolatorTime时间来控制x,y,z上的偏移
        camera.translate(100.0f - 100.0f * interpolatedTime, 150.0f * interpolatedTime - 150, 80.0f - 80.0f * interpolatedTime);
        //设置根据interpolatorTime时间在Y轴上旋转不同的角度
        camera.rotateY(360*interpolatedTime);
        //设置根据interpolatorTime时间在X轴上旋转不同的角度
        camera.rotateY(360*interpolatedTime);
        //获取Transformation参数的Matrix对象
        Matrix matrix = t.getMatrix();
        camera.getMatrix(matrix);
        matrix.preTranslate(-centerX,-centerY);
        matrix.postTranslate(centerX,centerY);
        camera.restore();
    }

}
