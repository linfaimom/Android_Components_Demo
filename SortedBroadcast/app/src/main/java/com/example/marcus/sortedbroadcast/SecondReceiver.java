package com.example.marcus.sortedbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by marcus on 16/2/26.
 */
public class SecondReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = getResultExtras(true);
        String first = bundle.getString("first");
        Toast.makeText(context,"收到的消息为:"+first,Toast.LENGTH_SHORT).show();
    }
}
