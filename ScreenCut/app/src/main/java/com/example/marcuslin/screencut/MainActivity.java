package com.example.marcuslin.screencut;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button cut;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView)findViewById(R.id.image);
        cut = (Button)findViewById(R.id.cut);
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getWindow().getDecorView();
                view.buildDrawingCache();
                Bitmap bitmap = view.getDrawingCache();
                image.setImageBitmap(bitmap);
            }
        });
    }
}
