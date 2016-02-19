package com.example.marcus.groupsend;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button select,send;
    EditText numbers,content;
    SmsManager smsManager;
    ArrayList<String> phoneNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        numbers = (EditText) findViewById(R.id.numbers);
        content = (EditText) findViewById(R.id.content);
        smsManager = SmsManager.getDefault();
        send = (Button) findViewById(R.id.send);
        select = (Button) findViewById(R.id.select);
        //发送信息
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PendingIntent pi = PendingIntent.getActivity(MainActivity.this,0,new Intent(),0);
                for (String number : phoneNumbers){
                    smsManager.sendTextMessage(number,null,content.getText().toString(),pi,null);
                }
                Toast.makeText(MainActivity.this,"群发成功!",Toast.LENGTH_SHORT).show();
            }
        });
        //选择群发号码,采用复选框方式
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取到系统联系人
                final Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
                BaseAdapter adapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return cursor.getCount();
                    }

                    @Override
                    public Object getItem(int position) {
                        return position;
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        cursor.moveToPosition(position);
                        //使用复选框
                        CheckBox checkBox = new CheckBox(MainActivity.this);
                        //获取当前位置选中的号码,并去掉空格和-,然后加入到checkBox中
                        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                .replace("-","")
                                .replace(" ","");
                        checkBox.setText(number);
                        if (isChecked(number)){
                            checkBox.setChecked(true);
                        }
                        //返还一个复选框视图
                        return checkBox;
                    }


                };

                View selectView = getLayoutInflater().inflate(R.layout.list,null);
                final ListView list = (ListView) selectView.findViewById(R.id.list);
                list.setAdapter(adapter);
                //利用对话框来加载View,View中是一个复选框列表
                new AlertDialog.Builder(MainActivity.this).setView(selectView).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //先将phoneNumbers里面清空
                                phoneNumbers.clear();
                                //遍历list里面的各个条目,如果是打勾的就加到这个phoneNumbers列表里
                                for (int i=0; i<list.getCount(); i++){
                                    CheckBox check = (CheckBox)list.getChildAt(i);
                                    if (check.isChecked()){
                                        phoneNumbers.add(check.getText().toString());
                                    }
                                }
                                //最终全部加到这个列表中,并在send里遍历各个号码,然后实现群发功能
                                numbers.setText(phoneNumbers.toString());
                            }
                        }).show();
            }
        });

    }

    //判断某个号码是否在群发范围内
    public boolean isChecked(String number) {
        for (String s : phoneNumbers){
            if (s.equals(number)){
                return true;
            }
        }
        return false;
    }
}
