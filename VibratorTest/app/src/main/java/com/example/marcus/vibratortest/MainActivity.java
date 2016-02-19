package com.example.marcus.vibratortest;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        vibrator.vibrate(500);
        return super.onTouchEvent(event);
    }
}
