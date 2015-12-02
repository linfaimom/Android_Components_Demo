package com.example.administrator.getphonenumber;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private Button select;
    final int PICK_CONTACT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        select = (Button)findViewById(R.id.button);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //设置Intent的Action属性
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //设置Intent的Type属性
                intent.setType("vnd.android.cursor.item/phone");
                startActivityForResult(intent, PICK_CONTACT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestId, int resultId, Intent intent){
        super.onActivityResult(requestId,resultId,intent);
        EditText name = (EditText)findViewById(R.id.name);
        EditText numbers = (EditText)findViewById(R.id.number);
        switch (resultId){
            case PICK_CONTACT :
                if (resultId == Activity.RESULT_OK){
                    //获取返回的数据
                    Uri contactData = intent.getData();
                    //查询联系人信息
                    Cursor cursor = managedQuery(contactData,null,null,null,null);
                    //遍历联系人
                    if (cursor.moveToFirst()){
                        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        //获取联系人名字
                        String names = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                        String phoneNumber = "无此联系人";

                        //根据联系人查询其电话号码
                        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactId,null,null);
                        //取出第一行
                        if (phones.moveToFirst()){
                            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        //关闭游标
                        phones.close();
                        //添加到姓名一栏
                        name.setText(names);
                        //添加到号码一栏
                        numbers.setText(phoneNumber);
                    }
                    //关闭游标
                    cursor.close();
                }
                break;

            default:
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
}
