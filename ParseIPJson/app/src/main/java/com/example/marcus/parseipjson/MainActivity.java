package com.example.marcus.parseipjson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import static com.example.marcus.parseipjson.ParseJsonResult.fetchResult;

public class MainActivity extends AppCompatActivity {
    private String ip;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        //AsyncTask
        Button fetch = (Button) findViewById(R.id.fetch);
        final EditText ipText = (EditText) findViewById(R.id.ip_text);
        if (fetch != null) {
            fetch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ipText != null) {
                        ip = ipText.getText().toString();
                    }
                    MyAsyncTask task = new MyAsyncTask();
                    task.execute(ip);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyAsyncTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String ip = params[0];
            return fetchResult(ip);
        }

        @Override
        protected void onPostExecute(String s) {
            content = (TextView) findViewById(R.id.content);
            int point = s.indexOf(":");
            String check = s.substring(1,point+2);
            if (check.equals("\"code\""+":"+0)) {
                Gson gson = new Gson();
                Foo foo = gson.fromJson(s,Foo.class);
                String[] keys = {"country","country_id","area","area_id",
                        "region","region_id","isp", "isp_id","ip"};
                String[] values = {foo.data.country,foo.data.country_id,
                        foo.data.area,foo.data.area_id,
                        foo.data.region,foo.data.region_id,
                        foo.data.isp,foo.data.isp_id,
                        foo.data.ip};
                //the length,only calculate once
                int length = keys.length;
                StringBuilder sb = new StringBuilder();
                for (int i=0; i<length; i++) {
                    sb.append(keys[i]).append(" : ").append(values[i]).append("\n");
                }
                if (content != null) {
                    content.setText(sb.toString());
                }
            }
            else {
                if (content != null) {
                    content.setText(getResources().getText(R.string.warning));
                }
            }
        }
    }
}
