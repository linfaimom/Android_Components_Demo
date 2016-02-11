package com.example.marcuslin.firstservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FirstService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("SERVICE CREATED!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("SERVICE STARTED");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("SERVICE DESTROYED!");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
