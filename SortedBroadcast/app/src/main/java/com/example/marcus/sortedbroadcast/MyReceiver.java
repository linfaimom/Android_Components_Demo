package com.example.marcus.sortedbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by marcus on 16/2/26.
 */
public class MyReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"接收到的Action为:"+
                intent.getAction()+"\n内容为:"+
                intent.getStringExtra("msg"),Toast.LENGTH_SHORT).show();
        //创建一个Bundle并存入数据
        Bundle bundle = new Bundle();
        bundle.putString("first","第一个BroadcastReceiver存入的消息");
        //将bundle放入结果中
        setResultExtras(bundle);
        //取消Broadcast的继续传播,那么SecondReceiver就接收不到消息了
        abortBroadcast();
    }
}
