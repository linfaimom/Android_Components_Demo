package com.example.marcus.asynctasktest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    private ImageView hello,hi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hi = (ImageView) findViewById(R.id.hi);
        hi.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.scene1));
        new TestAsyncTask().execute(R.drawable.scene1);
    }

    class TestAsyncTask extends AsyncTask<Integer,Void,Bitmap> {

        @Override
        protected void onPreExecute() {
            hello = (ImageView) findViewById(R.id.hello);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            int res = params[0];
            return new BitmapUtil(MainActivity.this,res,400,400).getRequiredBitmap();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            hello.setImageBitmap(bitmap);
        }
    }

}
