package com.example.marcuslin.sdcardexplorer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    ListView listView;
    Button button;
    TextView textView;
    File currentParent;
    File[] currentFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.list);
        button = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.text);
        File root = new File("/mnt/sdcard");
        if (root.exists()){
            currentParent = root;
            currentFiles = currentParent.listFiles();
            inflateListView(currentFiles);
        }
        else {
            Toast.makeText(MainActivity.this,"no sdcard mounted!",Toast.LENGTH_SHORT).show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!currentFiles[position].isDirectory()){
                    Toast.makeText(MainActivity.this,"this is a file!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"this is a directory!",Toast.LENGTH_SHORT).show();
                    currentParent = currentFiles[position];
                    currentFiles = currentFiles[position].listFiles();
                    inflateListView(currentFiles);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (currentParent.getCanonicalPath().equals("mnt/sdcard")){
                        Toast.makeText(MainActivity.this,"almost in the root!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        currentParent = currentParent.getParentFile();
                        currentFiles = currentParent.listFiles();
                        inflateListView(currentFiles);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void inflateListView(File[] currentFiles){
        List<Map<String,Object>> listItems = new ArrayList<>();
        for (int i=0;i<currentFiles.length;i++){
            Map<String,Object> items = new HashMap<>();
            if (currentFiles[i].isFile()){
                items.put("icon",R.drawable.file);
            }
            else {
                items.put("icon",R.drawable.folder);
            }
            items.put("filename",currentFiles[i].getName());
            listItems.add(items);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,listItems,R.layout.line,new String[]{"icon","filename"},new int[]{R.id.icon,R.id.filename});
        listView.setAdapter(adapter);
        try {
            textView.setText("当前路径:"+currentParent.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
