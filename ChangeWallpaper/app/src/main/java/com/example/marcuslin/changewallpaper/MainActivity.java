package com.example.marcuslin.changewallpaper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button start,stop;
    AlarmManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        Intent intent = new Intent(this,ChangeWallpaper.class);
        final PendingIntent pendingIntent = PendingIntent.getService(this,0,intent,0);
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //每5秒启动一次更换壁纸的操作
                manager.setRepeating(AlarmManager.RTC_WAKEUP,0,3000,pendingIntent);
                start.setEnabled(false);
                stop.setEnabled(true);
                Toast.makeText(MainActivity.this,"成功设置定时更换壁纸操作！",Toast.LENGTH_SHORT).show();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.cancel(pendingIntent);
                start.setEnabled(true);
                stop.setEnabled(false);
            }
        });
    }
}
