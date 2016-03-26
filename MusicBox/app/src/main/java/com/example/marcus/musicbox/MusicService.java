package com.example.marcus.musicbox;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by marcus on 16/3/26.
 */
public class MusicService extends Service {
    String[] musics = new String[]{"wish.mp3","promise.mp3","beautiful.mp3"};
    AssetManager am;
    MediaPlayer mediaPlayer;
    ServiceReceiver receiver;
    int status = 0x11;
    int current = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        am = getAssets();
        receiver = new ServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MainActivity.UPDATE_ACTION);
        registerReceiver(receiver,filter);
        mediaPlayer = new MediaPlayer();
        //设置完成播放自动开始下一曲的监听器
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                current++;
                if (current>=3){
                    current = 0;
                }
                //发送广播通知Activity更改文本框
                Intent intent = new Intent();
                intent.setAction(MainActivity.SERVICE_ACTION);
                intent.putExtra("current",current);
                sendBroadcast(intent);
                //准备并播放音乐
                prepareAndPlay(musics[current]);
            }
        });
    }

    public class ServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int control = intent.getIntExtra("control",-1);
            switch (control){
                //播放或暂停
                case 0 :
                    if (status == 0x11){
                        //准备播放音乐
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }
                    else if (status == 0x12){
                        mediaPlayer.pause();
                        status = 0x13;
                    }
                    else if (status == 0x13){
                        mediaPlayer.start();
                        status = 0x12;
                    }
                    break;
                //停止
                case 1 :
                    //如果原来正在播放或暂停
                    if (status==0x12 || status==0x13){
                        mediaPlayer.stop();
                        status = 0x11;
                    }
            }
            //发送广播通知Activity更改图标
            Intent sendIntent = new Intent();
            sendIntent.setAction(MainActivity.SERVICE_ACTION);
            sendIntent.putExtra("update",status);
            sendIntent.putExtra("current",current);
            sendBroadcast(sendIntent);
        }
    }

    public void prepareAndPlay(String music){
        //打开指定音乐文件
        try {
            AssetFileDescriptor afd = am.openFd(music);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            //准备声音
            mediaPlayer.prepare();
            //开始
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
