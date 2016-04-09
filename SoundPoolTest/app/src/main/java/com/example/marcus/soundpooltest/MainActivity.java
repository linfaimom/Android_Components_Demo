package com.example.marcus.soundpooltest;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bomb, shot, arrow;
    AudioAttributes attrs;
    SoundPool pool;
    Map<Integer,Integer> soundMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bomb = (Button) findViewById(R.id.bomb);
        shot = (Button) findViewById(R.id.shot);
        arrow = (Button) findViewById(R.id.arrow);
        attrs = new AudioAttributes.Builder()
                //设置音效使用场景
                .setUsage(AudioAttributes.USAGE_GAME)
                //设置音效类型
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        pool = new SoundPool.Builder()
                //设置音效池属性
                .setAudioAttributes(attrs)
                //设置最多可容纳的10个音频流
                .setMaxStreams(10)
                .build();
        soundMap.put(1,pool.load(this,R.raw.bomb,1));
        soundMap.put(2,pool.load(this,R.raw.shot,1));
        soundMap.put(3,pool.load(this,R.raw.arrow,1));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bomb :
                pool.play(soundMap.get(1),1,1,0,0,1);
                break;

            case R.id.shot :
                pool.play(soundMap.get(2),1,1,0,0,1);
                break;

            case R.id.arrow :
                pool.play(soundMap.get(3),1,1,0,0,1);
                break;
        }
    }
}
