package com.example.marcuslin.sqlitetest;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText edit1,edit2;
    SQLiteDatabase db;
    ListView list;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.listView);
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/mb.db3",null);
        button = (Button)findViewById(R.id.write);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit1 = (EditText)findViewById(R.id.text1);
                edit2 = (EditText)findViewById(R.id.text2);
                String title = edit1.getText().toString();
                String content = edit2.getText().toString();
                try {
                    db.execSQL("create table if not exists news_inf(_id integer"
                            + " primary key autoincrement,"
                            + " news_title varchar(50),"
                            + " news_content varchar(255))");
                    insertData(db,title,content);
                    Cursor cursor = db.rawQuery("select * from news_inf",null);
                    inflateCursor(cursor);
                } catch (SQLException e) {
                    e.getMessage();
                }

            }
        });
    }

    private void insertData(SQLiteDatabase db,String title,String content){
        String sql = "insert into news_inf values(null,?,?)";
        db.execSQL(sql,new String[]{title,content});
    }

    private void inflateCursor(Cursor cursor){
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(MainActivity.this,
                R.layout.show,
                cursor,
                new String[]{"news_title","news_content"},
                new int[]{R.id.tb_title,R.id.tb_content},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        list.setAdapter(cursorAdapter);
    }

    //重写退出方法,安全退出数据库
    @Override
    public void onDestroy(){
        super.onDestroy();
        if (db!=null && db.isOpen()){
            db.close();
        }
    }
}
