package com.example.administrator.contentprovider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private Button searcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searcher = (Button)findViewById(R.id.button);
        searcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //定义两个List来封装系统的联系人信息、指定联系人的电话号码以及其电子邮箱
                final ArrayList<String> names = new ArrayList<String>();
                final ArrayList<ArrayList<String>> details = new ArrayList<ArrayList<String>>();

                //使用ContentResolver来查找联系人数据
                Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
                //遍历以上查找的数据，获取所有的联系人数据
                while (cursor.moveToNext()) {
                    //当每次光标移动到下一位时
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    names.add(name);

                    //使用ContentResolver来获取该联系人的电话号码,基于上面的搜索结果
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = "+contactId, null, null);

                    //创建一个List用于存放该联系人的号码及邮箱
                    final ArrayList<String> detail = new ArrayList<String>();

                    //遍历以上查找的数据，获取该的联系人的(多个)电话号码
                    while (phones.moveToNext()) {
                        String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        detail.add("Phone number:"+number);
                    }
                    phones.close();

                    //使用ContentResolver来获取该联系人的邮箱
                    Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID+" = "+contactId, null, null);
                    //遍历以上查找的数据，获取该联系人的（多个）邮箱
                    while (emails.moveToNext()) {
                        String emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        detail.add("E-mail:"+emailAddress);
                    }
                    emails.close();

                    details.add(detail);
                }
                cursor.close();

                //加载result.xml布局文件
                View resultDialog = getLayoutInflater().inflate(R.layout.result,null);
                ExpandableListView list = (ExpandableListView)resultDialog.findViewById(R.id.list);
                //创建一个ExpandableListAdapter
                ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
                    @Override
                    public int getGroupCount() {
                        return names.size();
                    }

                    @Override
                    public int getChildrenCount(int i) {
                        return details.get(i).size();
                    }

                    @Override
                    public Object getGroup(int i) {
                        return names.get(i);
                    }

                    @Override
                    public Object getChild(int i, int i1) {
                        return details.get(i).get(i1);
                    }

                    @Override
                    public long getGroupId(int i) {
                        return i;
                    }

                    @Override
                    public long getChildId(int i, int i1) {
                        return i1;
                    }

                    @Override
                    public boolean hasStableIds() {
                        return true;
                    }

                    public TextView getTextView(){
                        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,64);
                        TextView textView = new TextView(MainActivity.this);
                        textView.setLayoutParams(lp);
                        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                        textView.setPadding(36, 0, 0, 0);
                        textView.setTextSize(20);
                        return  textView;
                    }

                    @Override
                    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
                        TextView textView = getTextView();
                        textView.setText(getGroup(i).toString());
                        return textView;
                    }

                    @Override
                    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
                        TextView textView = getTextView();
                        textView.setText(getChild(i,i1).toString());
                        return textView;
                    }

                    @Override
                    public boolean isChildSelectable(int i, int i1) {
                        return true;
                    }
                };

                //为ExpandableListView设置Adapter对象
                list.setAdapter(adapter);
                //使用对话框来显示查询结果
                new AlertDialog.Builder(MainActivity.this).setView(resultDialog).setPositiveButton("确定",null).show();
            }

        });
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
