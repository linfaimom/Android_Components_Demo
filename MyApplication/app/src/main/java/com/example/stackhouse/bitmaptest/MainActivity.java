package com.example.stackhouse.bitmaptest;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private Button next;
    private ImageView show;
    AssetManager assetManager = null;
    int currentId = 0;
    String[] images = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (ImageView)findViewById(R.id.iv);
        try {
            assetManager = getAssets();
            //获取assets目录下的所有文件
            images = assetManager.list("");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        next = (Button)findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果发生数组越界
                if (currentId >= images.length){
                    currentId = 0;
                }
                //找到下一个图片文件
                while (!images[currentId].endsWith("jpg"))
                {
                    currentId++;
                    if (currentId >= images.length){
                        currentId = 0;
                    }
                }
                InputStream assetFile = null;
                try{
                    //打开指定资源对应的文件输入流
                    assetFile = assetManager.open(images[currentId++]);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                BitmapDrawable bitmapDrawable = (BitmapDrawable)show.getDrawable();
                //如果图片还未回收，就强制回收
                if (bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled()){
                    bitmapDrawable.getBitmap().recycle();
                }
                //改变ImageView里显示的图片,使用二进制流打开
                show.setImageBitmap(BitmapFactory.decodeStream(assetFile));

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
