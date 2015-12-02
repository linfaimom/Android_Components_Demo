package com.example.administrator.firstresolver;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/11/15.
 */
public class MainActivity extends Activity{
    ContentResolver contentResolver;
    Uri uri = Uri.parse("content://com.godxiang.firstprovider/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        contentResolver = getContentResolver();
    }

    public void  query(View source) {
        //调用ContentResolver的query方法
        Cursor c = contentResolver.query(uri,null,"query_where",null,null);
        Toast.makeText(this,"远程返回的Cursor为:"+c,Toast.LENGTH_SHORT).show();
    }

    public void insert(View source) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "fuck your mother");
        Uri newUri = contentResolver.insert(uri,contentValues);
        Toast.makeText(this,"远程返回的Uri为:"+newUri,Toast.LENGTH_SHORT).show();
    }

    public void delete(View source) {
        int count = contentResolver.delete(uri,"delete_where",null);
        Toast.makeText(this,"远程删除记录为:"+count,Toast.LENGTH_SHORT).show();
    }

    public void update(View source) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "fuck your mother");
        int count = contentResolver.update(uri, contentValues, "name", null);
        Toast.makeText(this,"远程更新记录为:"+count,Toast.LENGTH_SHORT).show();
    }
}
