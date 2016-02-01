package com.example.marcuslin.ttstest;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextToSpeech tts;
    EditText content;
    Button speak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content = (EditText)findViewById(R.id.edit);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    //返回结果，成功为1失败为0
                    int result = tts.setLanguage(Locale.ENGLISH);
                    if (result!=TextToSpeech.LANG_COUNTRY_AVAILABLE && result!=TextToSpeech.LANG_AVAILABLE){
                        Toast.makeText(MainActivity.this,"TTS不支持不支持您的国家和语言",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        speak = (Button)findViewById(R.id.speak);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(content.getText().toString(),TextToSpeech.QUEUE_FLUSH,null,"speech");
            }
        });
    }
}
