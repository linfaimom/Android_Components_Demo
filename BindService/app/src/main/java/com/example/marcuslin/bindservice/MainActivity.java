package com.example.marcuslin.bindservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button bind,unbind,status;

    //用于保存获取到的service实例
    private BindService.MyBinder binder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("Service is binded!");
            //获取service,到时候可以用binder去干事情，比如获取内部状态等等
            binder = (BindService.MyBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("Service is disconnected!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent service = new Intent(this,BindService.class);
        bind = (Button)findViewById(R.id.bind);
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(service,connection,BIND_AUTO_CREATE);
            }
        });
        unbind = (Button)findViewById(R.id.unbind);
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);
            }
        });
        status = (Button)findViewById(R.id.status);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = binder.getCount();
                Toast.makeText(MainActivity.this,"The count is"+count,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
