package com.example.marcus.fragmenttest;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by marcus on 16/4/6.
 */
public class BookListFragment extends ListFragment{
    public interface Callbacks {
        public void onItemSelected(Integer id);
    }

    //到时候Fragment将调用此回调函数与所在的Activity交互
    private Callbacks callbacks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //为ListFragment设置Adapter
        setListAdapter(new ArrayAdapter<BookContent.Book>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                BookContent.ITEMS));
    }

    //当该Fragment被添加,显示到Activity时回调此方法
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //如果activity没有实现callbacks,就抛出异常
        if (!(activity instanceof Callbacks)){
            throw new IllegalStateException("未实现Callbacks接口");
        }
        callbacks = (Callbacks) activity;
    }

    //当该Fragment从它所属的Activity中被删除时调用的方法
    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    //当用户单击某列表项时激发回调该方法
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //激发Callbacks里的方法,即activity中实现的那个方法
        callbacks.onItemSelected(BookContent.ITEMS.get(position).id);
    }

}
