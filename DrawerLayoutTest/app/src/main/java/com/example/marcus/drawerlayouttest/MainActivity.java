package com.example.marcus.drawerlayouttest;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private String[] titles = {"林凤翔","李安","游绍威","刘子桢"};
    private ListView list;
    private DrawerLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        list = (ListView) findViewById(R.id.drawer_list);
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles));
    }
}
