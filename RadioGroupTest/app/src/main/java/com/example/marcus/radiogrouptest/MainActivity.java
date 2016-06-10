package com.example.marcus.radiogrouptest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.marcus.radiogrouptest.Fragments.Clothes;
import com.example.marcus.radiogrouptest.Fragments.Foods;
import com.example.marcus.radiogrouptest.Fragments.Hotels;
import com.example.marcus.radiogrouptest.Fragments.Me;
import com.example.marcus.radiogrouptest.Fragments.Transports;

/**
 * Created by marcus on 16/4/10.
 */
public class MainActivity extends FragmentActivity {
    private RadioGroup radioGroup;
    private RadioButton clothesTab,foodsTab,hotelsTab,transportsTab,meTab;
    private FrameLayout home;
    private static final int NUM_FRAGMENT = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initView();
    }

    private void initView() {
        home = (FrameLayout) findViewById(R.id.home);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);

        clothesTab = (RadioButton) findViewById(R.id.clothes);
        foodsTab = (RadioButton) findViewById(R.id.foods);
        hotelsTab = (RadioButton) findViewById(R.id.hotels);
        transportsTab = (RadioButton) findViewById(R.id.transports);
        meTab = (RadioButton) findViewById(R.id.me);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //记录点击位置index,根据index的值来启动相应fragment
                int index = 0;
                switch (checkedId){
                    case R.id.clothes :
                        index = 0;
                        break;
                    case R.id.foods :
                        index = 1;
                        break;
                    case R.id.hotels :
                        index = 2;
                        break;
                    case R.id.transports :
                        index = 3;
                        break;
                    case R.id.me :
                        index = 4;
                        break;
                }

                //设置第一个显示的fragment
                Fragment fragment = (Fragment) adapter.instantiateItem(home,index);
                adapter.setPrimaryItem(home,0,fragment);
                adapter.finishUpdate(home);
            }
        });


    }


    //Adapter
    FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            Fragment fragment = null;

            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0 :
                        fragment = new Clothes();
                        break;
                    case 1 :
                        fragment = new Foods();
                        break;
                    case 2 :
                        fragment = new Hotels();
                        break;
                    case 3 :
                        fragment = new Transports();
                        break;
                    case 4 :
                        fragment = new Me();
                        break;
                }

                return fragment;
            }

            @Override
            public int getCount() {
                return NUM_FRAGMENT;
            }
        };

}
