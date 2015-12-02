package com.example.administrator.intent;

import android.app.Activity;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ActionMenuItem;
import android.widget.EditText;

/**
 * Created by Administrator on 2015/8/29.
 */
public class SecondActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        final EditText show;
        show = (EditText)findViewById(R.id.show);
        Intent intent = getIntent();
        String action = intent.getAction();
        show.setText("Action name: "+ action);
    }
}
