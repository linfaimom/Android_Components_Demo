package com.example.marcus.picassotest;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView picasso = (ImageView) findViewById(R.id.picasso_image);
        Picasso.with(this)
                .load("http://img05.tooopen.com/images/20150830/tooopen_sy_140703593676.jpg")
                .rotate(180)
                .noPlaceholder()
                .fit()
                .into(picasso, new Callback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this,"Load Completed",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
