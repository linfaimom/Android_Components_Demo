package com.example.marcus.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by marcus on 16/2/24.
 */
public class MyReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"接受到的Intent的Action为:" + intent.getAction() + "\n 消息内容为:" +
                intent.getStringExtra("msg"),Toast.LENGTH_SHORT).show();
    }
}
