package com.example.marcuslin.sqliteopenhelpertest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button write,search;
    EditText words,explain,searchWords;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyOpenHelper myOpenHelper = new MyOpenHelper(this,"myDict.db3",null,1);
        final SQLiteDatabase db = myOpenHelper.getReadableDatabase();
        write = (Button)findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                words = (EditText)findViewById(R.id.words);
                explain = (EditText)findViewById(R.id.explain);
                String getWords = words.getText().toString();
                String getExplain = explain.getText().toString();
                insertData(db,getWords,getExplain);
                Cursor cursor = db.rawQuery("select * from test;",null);
                inflaterList(cursor);
            }
        });
        search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchWords = (EditText)findViewById(R.id.searchWords);
                String data = searchWords.getText().toString();
                Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                Bundle backup = new Bundle();
                backup.putString("data",data);
                intent.putExtras(backup);
                startActivity(intent);
            }
        });
    }

    private void insertData(SQLiteDatabase db, String words, String explain){
        String sql = "insert into test values(null,?,?);";
        db.execSQL(sql,new String[]{words,explain});
    }

    private void inflaterList(Cursor cursor){
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this,
                R.layout.line,
                cursor,
                new String[]{"words","explain"},
                new int[]{R.id.wordsTo,R.id.explainTo},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
    }

}
