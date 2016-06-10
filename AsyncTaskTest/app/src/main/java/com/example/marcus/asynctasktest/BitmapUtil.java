package com.example.marcus.asynctasktest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.util.Log;

/**
 * Created by marcus on 16/6/1.
 */
public class BitmapUtil {
    private final String TAG = "BitmapUtil测试情况:";
    private int res;
    private int requireW;
    private int requireH;
    private Context context;

    public BitmapUtil(Context context,@DrawableRes int res, int requireW, int requireH) {
        this.res = res;
        this.requireW = requireW;
        this.requireH = requireH;
        this.context = context;
    }

    public Bitmap getRequiredBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //将inJustDecodeBounds设置为true,表示不将位图加载到内存中,该功能用来读取资源的原始宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(),res,options);
        final int oldW = options.outWidth;
        final int oldH = options.outHeight;
        Log.d(TAG,"宽度:"+oldW);
        Log.d(TAG,"高度:"+oldH);
        options.inSampleSize = calculateInSampleSize(oldW,oldH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(),res,options);
    }

    private int calculateInSampleSize(int oldW, int oldH) {
        int sampleSize = 1;
        if (oldW > requireW || oldH > requireH) {
            //如果原图比所需的大,先将原图缩小为原来的二分之一,如果还是要比所需的大,则在此基础上继续缩小二分之一
            int halfW = oldW / 2;
            int halfH = oldH / 2;

            while ((halfW / sampleSize) > requireW && (halfH / sampleSize) > requireH) {
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }


}
