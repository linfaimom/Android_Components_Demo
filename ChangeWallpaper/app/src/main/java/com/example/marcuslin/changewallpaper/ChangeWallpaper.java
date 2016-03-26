package com.example.marcuslin.changewallpaper;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by marcuslin on 16/2/23.
 */
public class ChangeWallpaper extends Service {

    int[] pics;
    int current;
    WallpaperManager manager;

    @Override
    public void onCreate() {
        current=0;
        pics = new int[]{R.drawable.lijiang,R.drawable.qiao,R.drawable.shuangta,R.drawable.shui};
        manager = WallpaperManager.getInstance(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (current>=pics.length){
            current=0;
        }
        try {
            manager.setResource(pics[current++]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }
}
