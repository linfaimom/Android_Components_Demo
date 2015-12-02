package com.example.administrator.tabactivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/31.
 */
public class ThirdActivity extends Activity {
    private ListView lv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
        lv = (ListView)findViewById(R.id.lv);
        String[] names = {"sb","nb","2b","cb","eb"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,names);
        lv.setAdapter(arrayAdapter);
    }
}
