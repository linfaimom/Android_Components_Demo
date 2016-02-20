package com.example.marcus.alarmmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {

    Button setTime;
    AlarmManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTime = (Button) findViewById(R.id.setTime);
        //点击后弹出框来选中时间,默认显示为当前时间
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                //创建一个TimePickerDialog并把它显示出来
                new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //指定启动AlarmActivity
                        Intent intent = new Intent(MainActivity.this,AlarmActivity.class);
                        //从API文档中查阅得知,必须有下面这个Flag
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,0);
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(System.currentTimeMillis());
                        //根据用户的选择来更改启动时间
                        c.set(Calendar.HOUR,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        //获取AlarmManager对象
                        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        manager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                        //显示设置成功时候的信息
                        Toast.makeText(MainActivity.this,"设置成功!", Toast.LENGTH_SHORT).show();
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
            }
        });
    }
}
