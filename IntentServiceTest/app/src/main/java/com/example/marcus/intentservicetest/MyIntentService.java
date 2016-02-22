package com.example.marcus.intentservicetest;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by marcus on 16/2/13.
 */
public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //让线程暂停20s
        long endTime = System.currentTimeMillis() + 20 * 1000;
        System.out.println("onStartCommand!");
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
    }
}
