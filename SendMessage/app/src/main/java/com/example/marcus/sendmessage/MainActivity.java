package com.example.marcus.sendmessage;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button send;
    EditText number,content;
    SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smsManager = SmsManager.getDefault();
        number = (EditText)findViewById(R.id.number);
        content = (EditText)findViewById(R.id.content);
        send = (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,new Intent(),0);
                smsManager.sendTextMessage(number.getText().toString(),null,content.getText().toString(),pendingIntent,null);
                Toast.makeText(MainActivity.this,"短信发送成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
