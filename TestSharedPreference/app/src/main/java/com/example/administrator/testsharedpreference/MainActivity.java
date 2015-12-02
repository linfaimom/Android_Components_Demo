package com.example.administrator.testsharedpreference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    Button read;
    Button write;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        read = (Button)findViewById(R.id.read);
        write = (Button)findViewById(R.id.write);
       //获取Editor对象
        preferences = getSharedPreferences("fuck",MODE_PRIVATE);
        editor = preferences.edit();
        //读操作
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取字符串数据
                String time = preferences.getString("time", null);
                //读取数值数据
                int randNum = preferences.getInt("number", 0);

                String result = time == null ? "并木有时间输入" : "输入的时间为:" + time + "上一次的数值为:" + randNum;
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });

        //写操作
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日"+"hh:mm:ss");
                //存入当前时间
                editor.putString("time",simpleDateFormat.format(new Date()));
                //存入一个随机数
                editor.putInt("number",(int)(Math.random()*100));
                //提交所有存入的数据
                editor.commit();
            }
        });
    }

}
