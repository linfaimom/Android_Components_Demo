package com.example.marcus.fragmenttest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by marcus on 16/4/6.
 */
public class BookDetailFragment extends Fragment {

    public static final String ITEM_ID = "item_id";

    //保存该Fragment显示的Book对象
    public BookContent.Book book;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果该Fragment启动时包含了ITEM_ID这个参数
        if (getArguments().containsKey(ITEM_ID)){
            book = BookContent.ITEM_MAP.get(getArguments().getInt(ITEM_ID));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.main,container,false);
        if (book != null){
            ((TextView)layout.findViewById(R.id.title)).setText(book.title);
            ((TextView)layout.findViewById(R.id.desc)).setText(book.desc);
        }
        return layout;
    }
}
