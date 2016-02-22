package com.example.marcus.intentservicetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by marcus on 16/2/13.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long endTime = System.currentTimeMillis() + 20 * 1000;
        System.out.println("onStart!");
        while(System.currentTimeMillis() < endTime){
            synchronized(this){
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("---耗时任务执行完成---");
        return START_STICKY;
    }
}
