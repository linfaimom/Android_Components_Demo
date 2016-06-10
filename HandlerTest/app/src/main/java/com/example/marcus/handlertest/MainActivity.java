package com.example.marcus.handlertest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private TextView text;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                text.setText("Fuck your mother!");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textview);
        handler.post(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 0x123;
                handler.sendMessageDelayed(message,5000);
            }
        });
    }
}
