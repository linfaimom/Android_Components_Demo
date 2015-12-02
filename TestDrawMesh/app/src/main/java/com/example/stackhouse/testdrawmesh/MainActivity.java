package com.example.stackhouse.testdrawmesh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Vector;

public class MainActivity extends Activity {
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new myView(this, R.drawable.girls));
    }

    class myView extends View{
        //定义两个常量用于指定该图片横向和纵向上各划分20格
        private final int WIDTH = 20;
        private final int HEIGHT = 20;
        //记录顶点个数
        private final int COUNT = (WIDTH+1)*(HEIGHT+1);
        //定义一个verts数组用于保存各个顶点的坐标
        private float[] verts = new float[COUNT * 2];
        //定义一个origin数组作为verts未改变前的状态,便于后面针对verts做出修改
        private float[] origin = new float[COUNT * 2];

        public myView(Context context,int drawableID) {
            super(context);
            //根据指定资源加载图片
            bitmap = BitmapFactory.decodeResource(context.getResources(),drawableID);
            //获取位图里的宽和高
            int bitmapHeight = bitmap.getHeight();
            int bitmapWidth = bitmap.getWidth();

            int index = 0;
            //按比例将图片分割21*21个顶点
            for (int y=0;y<=HEIGHT;y++){
                float fy = (float)bitmapHeight * y / HEIGHT;
                for (int x=0;x<=WIDTH;x++,index++){
                    float fx = (float)bitmapWidth * x / WIDTH;
                    //初始化verts和origin数组,将顶点坐标均匀的保存在两个数组之中
                    verts[index*2+0]=origin[index*2+0]=fx;  //保存横坐标
                    verts[index*2+1]=origin[index*2+1]=fy;  //纵坐标
                }
            }
            //设置背景
            setBackgroundColor(Color.WHITE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //从（0,0）点开始扭曲
            canvas.drawBitmapMesh(bitmap,WIDTH,HEIGHT,verts,0,null,0,null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float cx = event.getX();
            float cy = event.getY();
            //计算当前触摸位置和每个顶点的距离
            for (int i=0;i<COUNT*2;i+=2){
                float dx = cx - origin[i+0];  //横坐标上的距离
                float dy = cy - origin[i+1];  //纵坐标上的距离
                float dd = dx*dx+dy*dy;
                float d = (float)Math.sqrt(dd);  //利用勾股定理算出距离
                //计算扭曲度，距离当前点（cx,cy）越远，扭曲度越小
                float pull = 80000 / (dd * d);

                if (pull >= 1){
                    verts[i+0]=cx;
                    verts[i+1]=cy;
                }
                else{
                    //在原先的基础上加上扭曲值
                    verts[i+0]=origin[i+0] + dx * pull;
                    verts[i+1]=origin[i+1] + dy * pull;
                }
            }
            //通知View组件进行重绘
            invalidate();
            //告知onTouchEvent该事件已处理
            return true;
        }
    }
}
