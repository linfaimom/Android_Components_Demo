package com.example.administrator.tabactivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by Administrator on 2015/8/31.
 */
public class FourthActivity extends Activity {
    private EditText editText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth);
        editText = (EditText)findViewById(R.id.et);
        editText.setText("Fuck your father"+R.string.hello_world);
    }
}
