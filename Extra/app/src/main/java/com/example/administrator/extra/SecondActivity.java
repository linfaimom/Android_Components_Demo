package com.example.administrator.extra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2015/8/30.
 */
public class SecondActivity extends Activity {
    private EditText tv;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        tv = (EditText)findViewById(R.id.tv);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String result = data.getString("data");
        tv.setText(result);
    }
}
