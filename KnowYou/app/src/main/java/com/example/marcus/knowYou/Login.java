package com.example.marcus.knowYou;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.marcus.knowYou.Fragments.Clothes;
import com.example.marcus.knowYou.Fragments.Foods;
import com.example.marcus.knowYou.Fragments.Hotels;
import com.example.marcus.knowYou.Fragments.Me;
import com.example.marcus.knowYou.Fragments.Transports;

/**
 * Created by marcus on 16/4/7.
 */

public class Login extends FragmentActivity {
    //Fragments
    private Class[] fragments = new Class[]{Clothes.class,Foods.class,Hotels.class, Transports.class,Me.class};
    //labels
    private String[] labels = new String[]{"衣","食","住","行","我"};
    //label_ids
    private String[] label_ids = new String[]{"clothes","foods","hotels","transports","me"};
    //pictures
    private int[] pictures = new int[]{R.drawable.clothes_change,R.drawable.foods_change,R.drawable.hotels_change,R.drawable.transport_change,R.drawable.me_change};
    //TabHost
    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        //initView
        initView();
    }

    private void initView() {
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);

        //get fragments' length
        int length = fragments.length;
        //set tab name for every fragment
        for (int i=0; i<length; i++){
            tabHost.addTab(tabHost.newTabSpec(label_ids[i]).setIndicator(getTabView(i)),fragments[i],null);
        }
    }

    private View getTabView(int i) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.tab_view,null);
        ((TextView)view.findViewById(R.id.tabname)).setText(labels[i]);
        ((ImageView)view.findViewById(R.id.tabpicture)).setImageResource(pictures[i]);
        return view;
    }
}
