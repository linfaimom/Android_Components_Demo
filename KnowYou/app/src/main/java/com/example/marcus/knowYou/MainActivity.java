package com.example.marcus.knowYou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login :
                Intent login = new Intent(MainActivity.this,Login.class);
                startActivity(login);
                break;

            case R.id.register :
                Intent register = new Intent(MainActivity.this,Register.class);
                startActivity(register);
                break;
        }
    }
}
