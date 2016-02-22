package com.example.marcus.phonestatelistener;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

public class MainActivity extends Activity {

    private TelephonyManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        PhoneStateListener listener = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state){
                    //无任何状态
                    case TelephonyManager.CALL_STATE_IDLE:
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        break;
                    //来电铃声响起的时候
                    case TelephonyManager.CALL_STATE_RINGING:
                        OutputStream os = null;
                        try {
                            os = openFileOutput("phoneList",MODE_APPEND);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        //用装饰流将来电号码输出至文件
                        PrintStream ps = new PrintStream(os);
                        ps.println(new Date() + " 来电: " + incomingNumber);
                        ps.close();
                        break;
                    default:
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        //监听电话通讯状态的改变
        manager.listen(listener,PhoneStateListener.LISTEN_CALL_STATE);
    }
}
