package com.example.stackhouse.myanimation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    String[] texts = new String[]{"asdas","asdas","dasd","adasd"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.list);
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i=0;i<texts.length;i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("listName",texts[i]);
            mapList.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,mapList,R.layout.test,new String[]{"listName"},new int[]{R.id.text});
        listView.setAdapter(adapter);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        listView.startAnimation(new MyAnimation(displayMetrics.ydpi/2,displayMetrics.xdpi/2,3500));
    }
}
