package com.example.administrator.menuxml;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView)findViewById(R.id.txt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //判断单机的是哪个菜单项，并针对性的做出响应
        switch (id){
            case R.id.font_10 : txt.setTextSize(10*2);break;
            case R.id.font_12 : txt.setTextSize(12*2);break;
            case R.id.font_14 : txt.setTextSize(14*2);break;
            case R.id.font_16 : txt.setTextSize(16*2);break;
            case R.id.font_18 : txt.setTextSize(18*2);break;
            case R.id.color_red : txt.setTextColor(Color.RED);break;
            case R.id.color_green : txt.setTextColor(Color.GREEN);break;
            case R.id.color_blue : txt.setTextColor(Color.BLUE);break;
            case R.id.fuck :
                Toast.makeText(MainActivity.this,"Fuck your mother!",Toast.LENGTH_LONG).show();
            default:
        }

        return super.onOptionsItemSelected(item);
    }
}
