package com.example.marcuslin.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by marcuslin on 16/2/11.
 */
public class BindService extends Service{

    boolean quit;

    int count=0;

    class MyBinder extends Binder {
        public int getCount(){
            return count;
        }
    }

    MyBinder binder = new MyBinder();

    //绑定时调用的方法
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("Service is binded!");
        return binder;
    }

    @Override
    public void onCreate() {
        System.out.println("Service is created!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!quit){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;                                //每秒给count加一
                }
            }
        }).start();
    }

    //异常断开连接时调用的方法
    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("Service is unbinded!");
        return true;
    }

    
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
        System.out.println("Service destoryed!");
    }
}
