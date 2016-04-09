package com.example.marcus.mediarecordertest;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button start, stop, play;
    private MediaRecorder recorder;
    private MediaPlayer player;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        play = (Button) findViewById(R.id.play);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        play.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        if (file.exists() && file != null){
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start :
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    Toast.makeText(this,"sd卡不存在,请插入sd卡",Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(this,"开始录音...",Toast.LENGTH_SHORT).show();
                try {
                    file = new File(Environment.getExternalStorageDirectory().getCanonicalFile() + "/sounds.mp3");
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    recorder.setOutputFile(file.getAbsolutePath());
                    recorder.prepare();
                    recorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this,"录音文件保存在:"+file.getAbsolutePath(),Toast.LENGTH_LONG).show();
                break;

            case R.id.stop :
                if (file.exists() && file != null){
                    recorder.stop();
                    recorder.release();
                    recorder = null;
                }
                break;

            case R.id.play :
                //to be fixed
                break;
        }
    }
}
