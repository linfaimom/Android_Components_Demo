package com.example.stackhouse.testfillscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Stackhouse on 2015/10/22.
 */
public class MyView extends View {
    private Bitmap girl;
    public MyView(Context context) {
        super(context);
        girl = BitmapFactory.decodeResource(getResources(), R.drawable.girls);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(girl,0,0,540,960);
        canvas.drawBitmap(bitmap,0,0,null);
    }
}
