package com.example.stackhouse.drawview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MyDraw myDraw = (MyDraw)findViewById(R.id.myDraw);
        switch (item.getItemId()){
            case R.id.blue:
                myDraw.paint.setColor(Color.BLUE);
                item.setCheckable(true);
                break;
            case R.id.green:
                myDraw.paint.setColor(Color.GREEN);
                item.setCheckable(true);
                break;
            case R.id.red:
                myDraw.paint.setColor(Color.RED);
                item.setCheckable(true);
                break;
            case R.id.width_12:
                myDraw.paint.setTextSize(12);
                item.setCheckable(true);
                break;
            case R.id.width_14:
                myDraw.paint.setTextSize(14);
                item.setCheckable(true);
                break;
            case R.id.width_16:
                myDraw.paint.setTextSize(16);
                item.setCheckable(true);
                break;
            default:
        }

        return true;
    }
}
