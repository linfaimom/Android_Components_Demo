package com.example.stackhouse.animationdrawable;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private Button play;
    private Button stop;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (Button)findViewById(R.id.play);
        stop = (Button)findViewById(R.id.stop);
        iv = (ImageView)findViewById(R.id.anim);
        final AnimationDrawable ad = (AnimationDrawable)iv.getBackground();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.stop();
            }
        });

        ad.start();
    }
}
