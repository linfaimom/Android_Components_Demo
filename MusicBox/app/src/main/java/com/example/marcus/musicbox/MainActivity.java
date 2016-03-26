package com.example.marcus.musicbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    static final String SERVICE_ACTION = "com.marcus.SERVICE_ACTION";
    static final String UPDATE_ACTION = "com.marcus.UPDATE_ACTION";
    ActivityReceiver receiver;
    //设置状态,0x11代表未播放,0x12代表正在播放,0x13代表暂停
    int status = 0x11;
    //各个组件
    ImageButton play,stop;
    EditText title,author;
    //歌曲信息
    String[] titles = new String[]{"心愿","约定","美丽新世界"};
    String[] authors = new String[]{"未知歌手","周蕙","伍佰"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化并注册接收器
        receiver = new ActivityReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SERVICE_ACTION);
        registerReceiver(receiver,filter);
        //初始化组件
        play = (ImageButton) findViewById(R.id.play);
        stop = (ImageButton) findViewById(R.id.stop);
        title = (EditText) findViewById(R.id.title);
        author = (EditText) findViewById(R.id.author);
        //创建Intent启动Service
        Intent intent = new Intent(this,MusicService.class);
        startService(intent);
        //为按钮设置图标及操作
        play.setImageResource(R.drawable.play);
        stop.setImageResource(R.drawable.stop);
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    public class ActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取Service返回的信息
            int current = intent.getIntExtra("current", -1);
            int update = intent.getIntExtra("update", -1);
            if (current>=0){
                title.setText(titles[current]);
                author.setText(authors[current]);
            }
            //更新按钮图标以及状态
            switch (update){
                //未播放
                case 0x11 :
                    play.setImageResource(R.drawable.play);
                    status = 0x11;
                    break;
                //正在播放
                case 0x12 :
                    play.setImageResource(R.drawable.pause);
                    status = 0x12;
                    break;
                //暂停时
                case 0x13 :
                    play.setImageResource(R.drawable.play);
                    status = 0x13;
                    break;
            }
        }
    }

    //向Service传送广播
    @Override
    public void onClick(View v) {
        //Activity向Service传送的是当前播放信息,Service根据此做出相应操作
        Intent intent = new Intent();
        intent.setAction(UPDATE_ACTION);
        //根据点击的按钮判断传0还是传1
        switch (v.getId()){
            case R.id.play :
                intent.putExtra("control",0);
                break;
            case R.id.stop :
                intent.putExtra("control",1);
                break;
        }
        sendBroadcast(intent);
    }
}
