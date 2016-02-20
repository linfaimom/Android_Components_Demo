package com.example.marcus.alarmmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

/**
 * Created by marcus on 16/2/20.
 * 此Activity播放音乐,并弹出一个对话框显示信息,
 */
public class AlarmActivity extends Activity{
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(this,R.raw.hello);
        mediaPlayer.start();
        new AlertDialog.Builder(this).setTitle("闹钟")
                .setMessage("闹钟响了,速度起床回老家!")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    //点击确定后,关闭音乐,并结束该Activity
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.stop();
                        AlarmActivity.this.finish();
                    }
                })
                .show();
    }
}
