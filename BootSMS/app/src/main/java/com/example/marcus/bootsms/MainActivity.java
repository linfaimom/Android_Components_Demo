package com.example.marcus.bootsms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by marcus on 16/3/26.
 */
public class MainActivity extends AppCompatActivity {
    Receiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        receiver = new Receiver();
    }
}
