package com.example.marcuslin.sqliteopenhelpertest;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by marcuslin on 16/1/30.
 */
public class ResultActivity extends Activity {

    EditText theWord,theExplain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        theWord = (EditText)findViewById(R.id.theWord);
        theExplain = (EditText)findViewById(R.id.theExplain);
        Intent intent = getIntent();
        Bundle getData = intent.getExtras();
        String result = getData.getString("data").toString();
        theWord.setText(result);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(this.getFilesDir().getParent().toString()+"/databases/myDict.db3",null,SQLiteDatabase.OPEN_READONLY);
        String sql = "select * from test"
                +" where words="
                +"'"+result+"'"
                +";";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        String backData = cursor.getString(2);
        theExplain.setText(backData);
    }
}
