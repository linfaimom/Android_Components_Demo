package com.example.marcus.webviewtest;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by marcus on 16/5/22.
 */

public class MyObject {
    private Context myContext;

    public MyObject(Context context) {
        myContext = context;
    }

    @JavascriptInterface
    public void showToast(String name) {
        Toast.makeText(myContext,"Hello,"+name,Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showList() {
        new AlertDialog.Builder(myContext)
                .setTitle("图书列表")
                .setItems(new String[]{"123","123","1234","1234","1234"},null)
                .setPositiveButton("确定",null)
                .setNegativeButton("取消",null)
                .create()
                .show();
    }

}
