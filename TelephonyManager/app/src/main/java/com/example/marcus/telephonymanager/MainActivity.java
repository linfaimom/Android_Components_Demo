package com.example.marcus.telephonymanager;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private ListView showList;
    String[] statusNames;
    String[] simState;
    String[] phonyType;
    ArrayList<String> statusValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取系统的TelephonyManager对象
        TelephonyManager tManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        //从array.xml中获取到String数组实例
        statusNames = getResources().getStringArray(R.array.statusNames);
        simState = getResources().getStringArray(R.array.simState);
        phonyType = getResources().getStringArray(R.array.phoneType);
        //获取设备编号
        statusValues.add(tManager.getDeviceId());
        //获取系统平台版本
        statusValues.add(tManager.getDeviceSoftwareVersion() != null ? tManager.getDeviceSoftwareVersion() : "未知");
        //获取网络运营商代号
        statusValues.add(tManager.getNetworkOperator());
        //获取网络运营商名称
        statusValues.add(tManager.getNetworkOperatorName());
        //获取手机网络类型
        statusValues.add(phonyType[tManager.getPhoneType()]);
        //获取设备所在位置
        statusValues.add(tManager.getCellLocation() != null ? tManager.getCellLocation().toString() : "未知");
        //获取sim卡国别
        statusValues.add(tManager.getSimCountryIso());
        //获取sim卡序列号
        statusValues.add(tManager.getSimSerialNumber());
        //获取sim卡状态
        statusValues.add(simState[tManager.getSimState()]);
        //获取ListView对象
        showList = (ListView)findViewById(R.id.show);
        ArrayList<Map<String,String>> status = new ArrayList<>();
        for (int i=0; i<statusValues.size(); i++){
            Map<String,String> map = new HashMap<>();
            map.put("name",statusNames[i]);
            map.put("value",statusValues.get(i));
            status.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                status,
                R.layout.status,
                new String[]{"name","value"},
                new int[]{R.id.name,R.id.value});
        showList.setAdapter(adapter);

    }
}
