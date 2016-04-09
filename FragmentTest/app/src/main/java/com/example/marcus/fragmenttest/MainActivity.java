package com.example.marcus.fragmenttest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by marcus on 16/4/6.
 */
public class MainActivity extends Activity implements BookListFragment.Callbacks{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
    }

    @Override
    public void onItemSelected(Integer id) {
        //创建Bundle,利用回调函数向Fragment传送数据
        Bundle bundle = new Bundle();
        bundle.putInt(BookDetailFragment.ITEM_ID,id);
        //创建BookDetailFragment对象
        BookDetailFragment fragment = new BookDetailFragment();
        //向其中传入参数
        fragment.setArguments(bundle);
        //使用fragment代替book_detail_container容器里显示的当前Fragment
        getFragmentManager().beginTransaction()
                .replace(R.id.book_detail_container,fragment)
                .commit();
    }
}
